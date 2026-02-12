package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.payloads.GiornoGaraDTO;
import GIOVANNILONGO.BE_GARA.payloads.GiornoGaraResponse;
import GIOVANNILONGO.BE_GARA.services.GiornoGaraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giornate-gara")
@RequiredArgsConstructor
public class GiornoGaraController {

    private final GiornoGaraService service;

    @GetMapping("/gara/{garaId}")
    public List<GiornoGaraResponse> listByGara(
            @PathVariable Long garaId
    ) {

        return service.getByGara(garaId)
                .stream()
                .map(g -> new GiornoGaraResponse(
                        g.getId(),
                        g.getNumero(),
                        g.getData()
                ))
                .toList();
    }

    @PostMapping("/{garaId}/giornate/chiudi")
    public void chiudiGiornata(@PathVariable Long garaId) {
        service.chiudiGiornataAttiva(garaId);
    }

    @GetMapping("/gara/{garaId}/dto")
    public List<GiornoGaraDTO> getByGara(@PathVariable Long garaId) {
        return service.getGiornateDTO(garaId);
    }

}
