package GIOVANNILONGO.BE_GARA.payloads;

public record PilotaResponse(
        Long id,
        String numeroGara,
        String nomePilota,
        String nomeCopilota,
        String team
) {
}
