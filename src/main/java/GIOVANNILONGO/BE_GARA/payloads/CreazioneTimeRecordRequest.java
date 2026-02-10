package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.Input;

public record CreazioneTimeRecordRequest(Long giornoGaraId, Long pilotaId, Long stazioneId, Input tipoInserimento) {
}
