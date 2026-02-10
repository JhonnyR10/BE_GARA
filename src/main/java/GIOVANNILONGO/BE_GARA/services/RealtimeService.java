package GIOVANNILONGO.BE_GARA.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtimeService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ClassificaService classificaService;
    private final GaraSnapshotService garaSnapshotService;

    //    public void notificaNuovoTempo(
//            TimeRecord tempo
//    ) {
//
//        TempoRealTimeDTO evento = new TempoRealTimeDTO(
//                tempo.getGiornoGara().getId(),
//                tempo.getPilota().getId(),
//                tempo.getStazione().getId(),
//                tempo.getStazione().getOrdine(),
//                tempo.getTimestampRilevato()
//        );
//
//        messagingTemplate.convertAndSend(
//                "/topic/gara/" + tempo.getGiornoGara().getGara().getId(),
//                evento
//        );
//
//        messagingTemplate.convertAndSend(
//                "/topic/giornata/" + tempo.getGiornoGara().getId(),
//                evento
//        );
//    }
//
//    public void notificaClassifica(
//            Long giornoGaraID
//    ) {
//
//        var classifica =
//                classificaService.classificaPerGiornata(
//                        giornoGaraID
//                );
//
//        messagingTemplate.convertAndSend(
//                "/topic/giornata/" + giornoGaraID + "/classifica",
//                new ClassificaRealTimeDTO(
//                        giornoGaraID,
//                        classifica
//                )
//        );
//    }
    public void inviaSnapshotGaraAttiva() {
        var snapshot = garaSnapshotService.snapshotGaraAttiva();
        messagingTemplate.convertAndSend(
                "/topic/snapshot",
                snapshot
        );
    }

    public void inviaClassificaGiornata(Long giornoGaraId) {
        var classifica = classificaService.classificaPerGiornata(giornoGaraId);
        messagingTemplate.convertAndSend(
                "/topic/classifica/giornata",
                classifica
        );
    }

    public void inviaClassificaTotale(Long garaId) {
        var classifica = classificaService.classificaTotale(garaId);
        messagingTemplate.convertAndSend(
                "/topic/classifica/totale",
                classifica
        );
    }
}


