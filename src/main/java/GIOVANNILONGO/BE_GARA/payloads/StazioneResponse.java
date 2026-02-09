package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.TipoStazione;

public record StazioneResponse(Long id, String nome, TipoStazione tipo, Integer ordine) {
}
