package GIOVANNILONGO.BE_GARA.snapshots;

import GIOVANNILONGO.BE_GARA.enums.StatoGara;

import java.time.LocalDate;

public record GaraSnapshotDTO(
        Long id,
        String nome,
        StatoGara stato,
        LocalDate dataInizio,
        LocalDate dataFine
) {
}
