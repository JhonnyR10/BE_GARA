package GIOVANNILONGO.BE_GARA.payloads;

public record RigaClassificaDTO(
        Integer posizione,
        Long pilotaId,
        String numeroGara,
        String nomePilota,
        Long tempoTotaleMillis
) {
}

