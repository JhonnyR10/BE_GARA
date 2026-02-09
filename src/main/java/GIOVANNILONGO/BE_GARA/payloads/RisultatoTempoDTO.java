package GIOVANNILONGO.BE_GARA.payloads;

import java.util.List;

public record RisultatoTempoDTO(
        Long pilotaId,
        List<ParzialeDTO> parziali,
        Long tempoTotaleMillis
) {
}

