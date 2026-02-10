package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.payloads.RigaClassificaDTO;
import GIOVANNILONGO.BE_GARA.services.ClassificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classifiche")
@RequiredArgsConstructor
public class ClassificaController {

    private final ClassificaService service;

    @GetMapping("/giornata/{giornataGaraId}")
    public List<RigaClassificaDTO> classificaGiornata(
            @PathVariable Long giornataGaraId
    ) {
        return service.classificaPerGiornata(giornataGaraId);
    }

    @GetMapping("/gara/{garaId}/totale")
    public List<RigaClassificaDTO> classificaTotale(@PathVariable Long garaId) {
        return service.classificaTotale(garaId);
    }

}

