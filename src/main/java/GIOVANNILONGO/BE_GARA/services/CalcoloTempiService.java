package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.TimeRecord;
import GIOVANNILONGO.BE_GARA.payloads.ParzialeDTO;
import GIOVANNILONGO.BE_GARA.payloads.RisultatoTempoDTO;
import GIOVANNILONGO.BE_GARA.repositories.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalcoloTempiService {

    private final TimeRecordRepository timeRecordRepository;

    public RisultatoTempoDTO calcolaPerPilota(
            Long giornoGaraId,
            Long pilotaId
    ) {

        List<TimeRecord> tempi = timeRecordRepository
                .findByGiornoGaraIdAndPilotaIdOrderByStazioneOrdineAsc(
                        giornoGaraId,
                        pilotaId
                );

        if (tempi.size() < 2) {
            return new RisultatoTempoDTO(
                    pilotaId,
                    List.of(),
                    null
            );
        }
        List<ParzialeDTO> parziali = new ArrayList<>();

        for (int i = 0; i < tempi.size() - 1; i++) {

            TimeRecord t1 = tempi.get(i);
            TimeRecord t2 = tempi.get(i + 1);

            long durata = Duration.between(
                    t1.getTimestampRilevato(),
                    t2.getTimestampRilevato()
            ).toMillis();

            parziali.add(new ParzialeDTO(
                    t1.getStazione().getOrdine(),
                    t2.getStazione().getOrdine(),
                    durata
            ));
        }

        long totale = Duration.between(
                tempi.get(0).getTimestampRilevato()
                ,
                tempi.get(tempi.size() - 1).getTimestampRilevato()
        ).toMillis();

        return new RisultatoTempoDTO(
                pilotaId,
                parziali,
                totale
        );
    }


}
