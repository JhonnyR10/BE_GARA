package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.entities.TimeRecord;
import GIOVANNILONGO.BE_GARA.payloads.CreazioneTimeRecordRequest;
import GIOVANNILONGO.BE_GARA.payloads.RisultatoTempoDTO;
import GIOVANNILONGO.BE_GARA.payloads.TimeRecordResponse;
import GIOVANNILONGO.BE_GARA.services.CalcoloTempiService;
import GIOVANNILONGO.BE_GARA.services.TimeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tempi")
@RequiredArgsConstructor
public class TimeRecordController {
    private final TimeRecordService timeRecordService;
    private final CalcoloTempiService calcoloTempiService;

    @PostMapping
    public TimeRecordResponse creaTempo(
            @RequestBody CreazioneTimeRecordRequest dto
    ) {
        TimeRecord tempo = timeRecordService.creaTempo(dto);
        return new TimeRecordResponse(
                tempo.getId(),
                tempo.getGiornoGara().getId(),
                tempo.getPilota().getId(),
                tempo.getStazione().getId(),
                tempo.getStazione().getOrdine(),
                tempo.getTimestampRilevato(),
                tempo.getTipoInserimento()
        );
    }

    @GetMapping("/risultati")
    public RisultatoTempoDTO risultatiPerPilota(
            @RequestParam Long giornoGaraId,
            @RequestParam Long pilotaId
    ) {
        return calcoloTempiService.calcolaPerPilota(
                giornoGaraId,
                pilotaId
        );
    }
}
