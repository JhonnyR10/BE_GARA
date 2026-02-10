package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.*;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import GIOVANNILONGO.BE_GARA.payloads.CreazioneTimeRecordRequest;
import GIOVANNILONGO.BE_GARA.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TimeRecordService {
    private final TimeRecordRepository timeRecordRepository;
    private final GiornoGaraRepository giornoGaraRepository;
    private final GiornoGaraService giornoGaraService;
    private final PilotaRepository pilotaRepository;
    private final StazioneRepository stazioneRepository;
    private final GaraRepository garaRepository;
    private final RealtimeService realtimeService;
    private final GaraService garaService;

    @Transactional
    public TimeRecord creaTempo(CreazioneTimeRecordRequest dto) {
        System.out.println("DTO RICEVUTO: " + dto);


        GiornoGara giornoGara = giornoGaraRepository.findById(dto.giornoGaraId())
                .orElseThrow(() -> new RuntimeException("Giornata gara non trovata"));
        Pilota pilota = pilotaRepository.findById(dto.pilotaId())
                .orElseThrow(() -> new RuntimeException("Pilota non trovato"));
        Stazione stazione = stazioneRepository.findById(dto.stazioneId())
                .orElseThrow(() -> new RuntimeException("Stazione non trovata"));

        Gara gara = giornoGara.getGara();
        if (gara.getStato() != StatoGara.ATTIVA) {
            throw new IllegalStateException(
                    "Impossibile inserire tempi: gara non ATTIVA"
            );
        }
        if (!Objects.equals(pilota.getGara().getId(), gara.getId())) {
            throw new IllegalStateException(
                    "Il pilota non appartiene alla gara della giornata"
            );
        }
        if (!Objects.equals(stazione.getGara().getId(), gara.getId())) {
            throw new IllegalStateException(
                    "La stazione non appartiene alla gara"
            );
        }
        if (timeRecordRepository.existsByGiornoGaraIdAndPilotaIdAndStazioneId(giornoGara.getId(), pilota.getId(), stazione.getId())
        ) {
            throw new IllegalStateException(
                    "Tempo già inserito per questa postazione"
            );
        }
        TimeRecord tempo = new TimeRecord();
        tempo.setGiornoGara(giornoGara);
        tempo.setPilota(pilota);
        tempo.setStazione(stazione);
        tempo.setTimestampRilevato(LocalDateTime.now());
        tempo.setTipoInserimento(dto.tipoInserimento());
        tempo.setDataCreazione(LocalDateTime.now());

        TimeRecord salvato = timeRecordRepository.save(tempo);


        //  realtimeService.notificaNuovoTempo(salvato);
        // realtimeService.notificaClassifica(
        //      salvato.getGiornoGara().getId()
        //);

        realtimeService.inviaSnapshotGaraAttiva();
        realtimeService.inviaClassificaGiornata(giornoGara.getId());


        if (stazione.getTipo() == TipoStazione.STOP) {

            boolean giornataCompleta = verificaGiornataCompleta(giornoGara);

            if (giornataCompleta) {
                boolean ultimaGiornata =
                        giornoGaraService.chiudiGiornataAttiva(gara.getId());

                if (ultimaGiornata) {
                    garaService.chiudiGara(gara.getId());
                }
            }
        }


        return salvato;
    }

    private boolean verificaGiornataCompleta(GiornoGara giornoGara) {

        Long garaId = giornoGara.getGara().getId();

        long numeroPiloti =
                pilotaRepository.countByGaraId(garaId);

        long numeroStop =
                timeRecordRepository.countByGiornoGaraIdAndStazioneTipo(
                        giornoGara.getId(),
                        TipoStazione.STOP
                );

        return numeroPiloti == numeroStop;
    }


}
