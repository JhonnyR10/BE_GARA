package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.entities.Stazione;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;
import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import GIOVANNILONGO.BE_GARA.exceptions.DomainException;
import GIOVANNILONGO.BE_GARA.payloads.CreazioneGaraRequest;
import GIOVANNILONGO.BE_GARA.payloads.GaraListDTO;
import GIOVANNILONGO.BE_GARA.payloads.UpdateGaraRequest;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.StazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GaraService {

    private final GaraRepository garaRepository;
    private final GiornoGaraRepository giornoGaraRepository;
    private final StazioneRepository stazioneRepository;

    public Gara createGara(CreazioneGaraRequest dto) {

        garaRepository.findByStato(StatoGara.ATTIVA).ifPresent(r -> {
            throw new RuntimeException("Esiste già una gara attiva");
        });

        LocalDate dataInizio = dto.dataInizio();

        LocalDate dataFine = dto.dataFine() != null
                ? dto.dataFine()
                : dto.dataInizio();

        if (dataFine.isBefore(dataInizio)) {
            throw new RuntimeException("La Data di fine non può essere prima della data di inizio");
        }

        int numeroStazioni = dto.numeroStazioni() != null
                ? dto.numeroStazioni()
                : 3;
        if (numeroStazioni < 3) {
            throw new IllegalStateException("Il numero minimo di stazioni è 3");
        }

        Gara gara = new Gara();
        gara.setNome(dto.nome());
        gara.setDataInizio(dataInizio);
        gara.setDataFine(dataFine);
        gara.setNumeroStazioni(numeroStazioni);
        gara.setStato(StatoGara.BOZZA);

        return garaRepository.save(gara);
    }


    private void generaGiornate(Gara gara) {

        LocalDate corrente = gara.getDataInizio();
        int numero = 1;

        while (!corrente.isAfter(gara.getDataFine())) {

            GiornoGara g = new GiornoGara();
            g.setNumero(numero++);
            g.setData(corrente);
            g.setGara(gara);
            g.setStato(StatoGiornata.NON_INIZIATA);

            giornoGaraRepository.save(g);

            corrente = corrente.plusDays(1);
        }
    }

    private void generaStazioni(Gara gara, int numeroStazioni) {
        if (numeroStazioni < 3) {
            throw new IllegalStateException(
                    "Il numero minimo di postazioni è 3"
            );
        }

        creaStazione(gara, 1, "START", TipoStazione.START);
        for (int i = 2; i < numeroStazioni; i++) {
            creaStazione(gara, i, "INTERMEDIA " + (i - 1), TipoStazione.INTERMEDIA);
        }
        creaStazione(gara, numeroStazioni, "STOP", TipoStazione.STOP);

    }

    private void creaStazione(Gara gara, int ordine, String nome, TipoStazione tipo) {
        Stazione s = new Stazione();
        s.setGara(gara);
        s.setOrdine(ordine);
        s.setNome(nome);
        s.setTipo(tipo);

        stazioneRepository.save(s);
    }

    private void attivaPrimaGiornata(Gara gara) {

        List<GiornoGara> giornate =
                giornoGaraRepository.findByGaraIdOrderByData(gara.getId());

        if (giornate.isEmpty()) {
            throw new IllegalStateException("La gara non ha giornate");
        }

        GiornoGara prima = giornate.get(0);
        prima.setStato(StatoGiornata.ATTIVA);
    }


    public Gara apriGara(Long garaId) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new RuntimeException("Gara non trovata"));

        if (gara.getStato() != StatoGara.BOZZA) {
            throw new RuntimeException("Solo le gare in BOZZA possono essere aperte");
        }

        gara.setStato(StatoGara.ATTIVA);
        generaGiornate(gara);
        attivaPrimaGiornata(gara);
        generaStazioni(gara, gara.getNumeroStazioni());

        return garaRepository.save(gara);
    }

    public Gara chiudiGara(Long garaId) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new RuntimeException("Gara non trovata"));

        if (gara.getStato() != StatoGara.ATTIVA) {
            throw new RuntimeException("Solo le gare APERTE possono essere chiuse");
        }

        gara.setStato(StatoGara.CONCLUSA);

        return garaRepository.save(gara);
    }

    public List<GaraListDTO> getAllGare() {
        return garaRepository.findAllByOrderByDataInizioDesc()
                .stream()
                .map(g -> new GaraListDTO(
                        g.getId(),
                        g.getNome(),
                        g.getStato(),
                        g.getDataInizio(),
                        g.getDataFine(),
                        g.getNumeroStazioni()
                ))
                .toList();
    }

    @Transactional
    public Gara updateGara(Long garaId, UpdateGaraRequest dto) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new DomainException("Gara non trovata"));

        if (gara.getStato() != StatoGara.BOZZA) {
            throw new DomainException("Modifiche consentite solo su gara in BOZZA");
        }

        if (dto.nome() != null) {
            gara.setNome(dto.nome());
        }
        LocalDate nuovaDataInizio =
                dto.dataInizio() != null ? dto.dataInizio() : gara.getDataInizio();

        LocalDate nuovaDataFine =
                dto.dataFine() != null
                        ? dto.dataFine()
                        : (gara.getDataFine() != null
                        ? gara.getDataFine()
                        : nuovaDataInizio);

        if (nuovaDataFine.isBefore(nuovaDataInizio)) {
            throw new DomainException("La data fine non può essere prima della data inizio");
        }

        gara.setDataInizio(nuovaDataInizio);
        gara.setDataFine(nuovaDataFine);

        if (dto.numeroStazioni() != null) {

            if (dto.numeroStazioni() < 3) {
                throw new DomainException("Il numero minimo di stazioni è 3");
            }

            gara.setNumeroStazioni(dto.numeroStazioni());
        }

        return gara;
    }


}
