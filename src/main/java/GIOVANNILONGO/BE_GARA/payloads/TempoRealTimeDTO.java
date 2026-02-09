package GIOVANNILONGO.BE_GARA.payloads;

import java.time.LocalDateTime;

public record TempoRealTimeDTO(
        Long giornoGaraId,
        Long pilotaId,
        Long stazioneId,
        Integer ordinePostazione,
        LocalDateTime timestamp
) {
}

