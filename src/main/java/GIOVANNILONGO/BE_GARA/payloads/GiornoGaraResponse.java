package GIOVANNILONGO.BE_GARA.payloads;

import java.time.LocalDate;

public record GiornoGaraResponse(Long id, Integer numero, LocalDate data) {
}
