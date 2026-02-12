package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.entities.Pilota;
import GIOVANNILONGO.BE_GARA.payloads.CreazionePilotaRequest;
import GIOVANNILONGO.BE_GARA.payloads.PilotaDTO;
import GIOVANNILONGO.BE_GARA.payloads.PilotaResponse;
import GIOVANNILONGO.BE_GARA.payloads.UpdatePilotaRequest;
import GIOVANNILONGO.BE_GARA.services.PilotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piloti")
@RequiredArgsConstructor
public class PilotaController {

    private final PilotaService service;

    @PostMapping
    public PilotaResponse create(@RequestBody CreazionePilotaRequest dto) {
        Pilota p = service.create(dto);

        return new PilotaResponse(
                p.getId(),
                p.getNumeroGara(),
                p.getNomePilota(),
                p.getNomeCopilota(),
                p.getTeam()
        );
    }

    @GetMapping("/gara/{garaId}")
    public List<PilotaResponse> listByGara(@PathVariable Long garaId) {
        return service.listByGara(garaId)
                .stream()
                .map(p -> new PilotaResponse(
                        p.getId(),
                        p.getNumeroGara(),
                        p.getNomePilota(),
                        p.getNomeCopilota(),
                        p.getTeam()
                ))
                .toList();
    }

    @DeleteMapping("/{pilotaId}")
    public void delete(@PathVariable Long pilotaId) {
        service.deletePilota(pilotaId);
    }

    @PatchMapping("/{pilotaId}")
    public PilotaDTO update(
            @PathVariable Long pilotaId,
            @RequestBody UpdatePilotaRequest dto
    ) {
        return service.updatePilota(pilotaId, dto);
    }


}
