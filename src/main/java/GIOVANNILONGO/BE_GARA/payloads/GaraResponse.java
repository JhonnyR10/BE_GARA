package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.StatoGara;

public record GaraResponse(Long id, String nome, StatoGara stato) {
}
