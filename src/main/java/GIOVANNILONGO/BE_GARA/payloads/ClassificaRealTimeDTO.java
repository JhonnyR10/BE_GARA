package GIOVANNILONGO.BE_GARA.payloads;

import java.util.List;

public record ClassificaRealTimeDTO(
        Long giornoGaraId,
        List<RigaClassificaDTO> classifica
) {
}
