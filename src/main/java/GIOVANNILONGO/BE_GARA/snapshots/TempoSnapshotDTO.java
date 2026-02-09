package GIOVANNILONGO.BE_GARA.snapshots;

import java.time.LocalDateTime;

public record TempoSnapshotDTO(
        Long id,
        Long pilotaId,
        Long postazioneId,
        Integer ordinePostazione,
        LocalDateTime timestamp
) {
}

