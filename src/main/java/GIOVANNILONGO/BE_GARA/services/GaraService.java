package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.entities.Stazione;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import GIOVANNILONGO.BE_GARA.payloads.CreazioneGaraRequest;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.StazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public Gara getActiveRace() {
        return garaRepository.findByStato(StatoGara.ATTIVA).orElseThrow();
    }

    private void generaGiornate(Gara gara) {

        LocalDate corrente = gara.getDataInizio();
        int numero = 1;

        while (!corrente.isAfter(gara.getDataFine())) {

            GiornoGara g = new GiornoGara();
            g.setNumero(numero++);
            g.setData(corrente);
            g.setGara(gara);

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

    public Gara apriGara(Long garaId) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new RuntimeException("Gara non trovata"));

        if (gara.getStato() != StatoGara.BOZZA) {
            throw new RuntimeException("Solo le gare in BOZZA possono essere aperte");
        }

        gara.setStato(StatoGara.ATTIVA);
        generaGiornate(gara);
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


}
