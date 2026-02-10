package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.entities.Pilota;
import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;
import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import GIOVANNILONGO.BE_GARA.payloads.RigaClassificaDTO;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.PilotaRepository;
import GIOVANNILONGO.BE_GARA.repositories.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassificaService {

    private final GiornoGaraRepository giornoGaraRepository;
    private final PilotaRepository pilotaRepository;
    private final CalcoloTempiService calcoloTempiService;
    private final TimeRecordRepository timeRecordRepository;

    public List<RigaClassificaDTO> classificaPerGiornata(
            Long giornoGaraId
    ) {

        GiornoGara giornata = giornoGaraRepository.findById(giornoGaraId)
                .orElseThrow(() ->
                        new RuntimeException("Giornata gara non trovata")
                );

        List<Pilota> piloti =
                pilotaRepository.findByGaraId(
                        giornata.getGara().getId()
                );

        List<RigaClassificaDTO> righe = new ArrayList<>();

        for (Pilota c : piloti) {

            var risultato =
                    calcoloTempiService.calcolaPerPilota(
                            giornoGaraId,
                            c.getId()
                    );

            Long tempoOrdinamento = null;

            // 1️⃣ STOP → tempo totale
            if (risultato.tempoTotaleMillis() != null) {
                tempoOrdinamento = risultato.tempoTotaleMillis();
            }
            // 2️⃣ parziali → ultimo parziale
            else if (!risultato.parziali().isEmpty()) {
                tempoOrdinamento =
                        risultato.parziali()
                                .get(risultato.parziali().size() - 1)
                                .durataMillis();
            }
            // 3️⃣ SOLO START → tempo = 0
            else {
                boolean haStart = timeRecordRepository
                        .existsByGiornoGaraIdAndPilotaId(giornoGaraId, c.getId());

                if (haStart) {
                    tempoOrdinamento = 0L;
                }
            }

            if (tempoOrdinamento != null) {
                righe.add(new RigaClassificaDTO(
                        null,
                        c.getId(),
                        c.getNumeroGara(),
                        c.getNomePilota(),
                        tempoOrdinamento
                ));
            }
        }


        righe.sort(
                Comparator.comparingLong(
                        RigaClassificaDTO::tempoTotaleMillis
                )
        );

        int pos = 1;
        List<RigaClassificaDTO> classifica = new ArrayList<>();

        for (RigaClassificaDTO r : righe) {
            classifica.add(new RigaClassificaDTO(
                    pos++,
                    r.pilotaId(),
                    r.numeroGara(),
                    r.nomePilota(),
                    r.tempoTotaleMillis()
            ));
        }

        return classifica;
    }

    public List<RigaClassificaDTO> classificaTotale(Long garaId) {

        List<GiornoGara> giornate =
                giornoGaraRepository.findByGaraIdOrderByNumeroAsc(garaId);

        List<GiornoGara> daConsiderare = giornate.stream()
                .filter(g -> g.getStato() == StatoGiornata.CONCLUSA)
                .collect(Collectors.toList());

        giornate.stream()
                .filter(g -> g.getStato() == StatoGiornata.ATTIVA)
                .findFirst()
                .ifPresent(attiva -> {

                    boolean esisteStop =
                            timeRecordRepository
                                    .existsByGiornoGaraIdAndStazioneTipo(
                                            attiva.getId(),
                                            TipoStazione.STOP
                                    );

                    if (esisteStop) {
                        daConsiderare.add(attiva);
                    }
                });

        List<Pilota> piloti =
                pilotaRepository.findByGaraId(garaId);

        List<RigaClassificaDTO> righe = new ArrayList<>();
        for (Pilota p : piloti) {

            long totale = 0;
            boolean haAlmenoUnTempo = false;

            for (GiornoGara g : daConsiderare) {

                var risultato =
                        calcoloTempiService.calcolaPerPilota(
                                g.getId(),
                                p.getId()
                        );

                if (risultato.tempoTotaleMillis() != null) {
                    totale += risultato.tempoTotaleMillis();
                    haAlmenoUnTempo = true;
                }
            }

            if (haAlmenoUnTempo) {
                righe.add(new RigaClassificaDTO(
                        null,
                        p.getId(),
                        p.getNumeroGara(),
                        p.getNomePilota(),
                        totale
                ));
            }
        }
        righe.sort(
                Comparator.comparingLong(
                        RigaClassificaDTO::tempoTotaleMillis
                )
        );
        int pos = 1;
        List<RigaClassificaDTO> classifica = new ArrayList<>();

        for (RigaClassificaDTO r : righe) {
            classifica.add(new RigaClassificaDTO(
                    pos++,
                    r.pilotaId(),
                    r.numeroGara(),
                    r.nomePilota(),
                    r.tempoTotaleMillis()
            ));
        }

        return classifica;
    }


}



