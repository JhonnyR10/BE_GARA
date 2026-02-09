package GIOVANNILONGO.BE_GARA.payloads;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp
) {
}

