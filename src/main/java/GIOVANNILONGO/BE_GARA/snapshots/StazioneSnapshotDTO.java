package GIOVANNILONGO.BE_GARA.snapshots;

import GIOVANNILONGO.BE_GARA.enums.TipoStazione;

public record StazioneSnapshotDTO(
        Long id,
        String nome,
        Integer ordine,
        TipoStazione tipo
) {
}

