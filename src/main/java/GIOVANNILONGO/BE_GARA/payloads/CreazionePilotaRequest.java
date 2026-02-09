package GIOVANNILONGO.BE_GARA.payloads;

public record CreazionePilotaRequest(
        Long garaId,
        String numeroGara,
        String nomePilota,
        String nomeCopilota,
        String team

) {
}
