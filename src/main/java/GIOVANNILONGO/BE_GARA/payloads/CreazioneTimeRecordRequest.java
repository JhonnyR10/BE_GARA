package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.Input;

import java.time.LocalDateTime;

public record CreazioneTimeRecordRequest(Long giornoGaraId, Long pilotaId, Long stazioneId, LocalDateTime timeStamp,
                                         Input tipoInserimento) {
}
