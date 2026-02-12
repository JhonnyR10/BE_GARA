package GIOVANNILONGO.BE_GARA.payloads;

public record UpdatePilotaRequest(
        String numeroGara,
        String nomePilota,
        String nomeCopilota,
        String team
) {
}

