package GIOVANNILONGO.BE_GARA.payloads;

import GIOVANNILONGO.BE_GARA.enums.StatoGara;

import java.time.LocalDate;

public record GaraListDTO(
        Long id,
        String nome,
        StatoGara stato,
        LocalDate dataInizio,
        LocalDate dataFine,
        Integer numeroStazioni
) {
}

