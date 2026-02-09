package GIOVANNILONGO.BE_GARA.payloads;

import java.time.LocalDate;

public record CreazioneGaraRequest(String nome, LocalDate dataInizio, LocalDate dataFine, Integer numeroStazioni) {
}
