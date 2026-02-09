package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.Input;

import java.time.LocalDateTime;

public record TimeRecordResponse(
        Long id,
        Long giornataGaraId,
        Long competitorId,
        Long postazioneId,
        Integer ordinePostazione,
        LocalDateTime timestamp,
        Input tipoInserimento
) {
}

