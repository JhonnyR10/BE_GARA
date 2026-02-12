package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.payloads.CreazioneGaraRequest;
import GIOVANNILONGO.BE_GARA.payloads.GaraListDTO;
import GIOVANNILONGO.BE_GARA.payloads.GaraResponse;
import GIOVANNILONGO.BE_GARA.payloads.UpdateGaraRequest;
import GIOVANNILONGO.BE_GARA.services.GaraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gare")
@RequiredArgsConstructor
public class GaraController {

    private final GaraService garaService;

    @GetMapping
    public List<GaraListDTO> getAll() {
        return garaService.getAllGare();
    }


    @PostMapping
    public GaraResponse create(@RequestBody CreazioneGaraRequest dto) {
        Gara gara = garaService.createGara(dto);

        return new GaraResponse(
                gara.getId(),
                gara.getNome(),
                gara.getStato()
        );
    }

    @PatchMapping("/{id}/apri")
    public ResponseEntity<Gara> apriGara(@PathVariable Long id) {
        return ResponseEntity.ok(garaService.apriGara(id));
    }

    @PatchMapping("/{id}/chiudi")
    public ResponseEntity<Gara> chiudiGara(@PathVariable Long id) {
        return ResponseEntity.ok(garaService.chiudiGara(id));
    }

    @PatchMapping("/{garaId}")
    public Gara update(
            @PathVariable Long garaId,
            @RequestBody UpdateGaraRequest dto
    ) {
        return garaService.updateGara(garaId, dto);
    }


}
