package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;

import java.time.LocalDate;

public record GiornoGaraDTO(
        Long id,
        Integer numero,
        LocalDate data,
        StatoGiornata stato
) {
}

