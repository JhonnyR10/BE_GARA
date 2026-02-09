package GIOVANNILONGO.BE_GARA.controllers;

import GIOVANNILONGO.BE_GARA.services.GaraSnapshotService;
import GIOVANNILONGO.BE_GARA.snapshots.GaraAttivaSnapshotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gara/attiva")
@RequiredArgsConstructor
public class GaraSnapshotController {

    private final GaraSnapshotService snapshotService;

    @GetMapping("/snapshot")
    public GaraAttivaSnapshotDTO snapshot() {
        return snapshotService.snapshotGaraAttiva();
    }
}

