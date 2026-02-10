package GIOVANNILONGO.BE_GARA.snapshots;

import java.time.LocalDateTime;

public record TempoSnapshotDTO(
        Long id,
        Long pilotaId,
        Long stazioneId,
        Integer ordineStazione,
        LocalDateTime timestamp
) {
}

