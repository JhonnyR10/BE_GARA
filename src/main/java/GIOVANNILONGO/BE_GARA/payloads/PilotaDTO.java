package GIOVANNILONGO.BE_GARA.payloads;

public record PilotaDTO(
        Long garaId,
        String numeroGara,
        String nomePilota,
        String nomeCopilota,
        String team
) {
}
