package GIOVANNILONGO.BE_GARA.snapshots;

import GIOVANNILONGO.BE_GARA.payloads.RigaClassificaDTO;

import java.util.List;

public record GaraAttivaSnapshotDTO(
        GaraSnapshotDTO gara,
        GiornoGaraSnapshotDTO giornata,
        List<StazioneSnapshotDTO> stazioni,
        List<PilotaSnapshotDTO> piloti,
        List<TempoSnapshotDTO> tempi,
        List<RigaClassificaDTO> classifica,
        boolean ultimaGiornata
) {
}

