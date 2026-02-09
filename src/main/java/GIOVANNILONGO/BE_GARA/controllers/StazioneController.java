package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.payloads.StazioneResponse;
import GIOVANNILONGO.BE_GARA.services.StazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stazioni")
@RequiredArgsConstructor
public class StazioneController {

    private final StazioneService service;

    @GetMapping("/gara/{garaId}")
    public List<StazioneResponse> listByGara(
            @PathVariable Long garaId
    ) {

        return service.listByGara(garaId)
                .stream()
                .map(p -> new StazioneResponse(
                        p.getId(),
                        p.getNome(),
                        p.getTipo(),
                        p.getOrdine()
                ))
                .toList();
    }
}

