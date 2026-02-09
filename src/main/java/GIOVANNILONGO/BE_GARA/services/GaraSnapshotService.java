package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.*;
import GIOVANNILONGO.BE_GARA.exceptions.DomainException;
import GIOVANNILONGO.BE_GARA.repositories.*;
import GIOVANNILONGO.BE_GARA.snapshots.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GaraSnapshotService {

    private final GaraRepository garaRepository;
    private final GiornoGaraRepository giornoGaraRepository;
    private final StazioneRepository stazioneRepository;
    private final PilotaRepository pilotaRepository;
    private final TimeRecordRepository timeRecordRepository;
    private final ClassificaService classificaService;

    public GaraAttivaSnapshotDTO snapshotGaraAttiva() {

        Gara gara = garaRepository.findGaraAttiva()
                .orElseThrow(() ->
                        new DomainException("Nessuna gara attiva")
                );

        GiornoGara giornata =
                giornoGaraRepository.findByGaraId(gara.getId())
                        .orElseThrow(() ->
                                new DomainException("Nessuna giornata trovata per la gara attiva")
                        );

        return new GaraAttivaSnapshotDTO(
                mapGara(gara),
                mapGiornata(giornata),
                stazioneRepository.findByGaraIdOrderByOrdine(gara.getId())
                        .stream().map(this::mapPostazione).toList(),
                pilotaRepository.findByGaraId(gara.getId())
                        .stream().map(this::mapPilota).toList(),
                timeRecordRepository.findByGiornoGaraId(giornata.getId())
                        .stream().map(this::mapTempo).toList(),
                classificaService.classificaPerGiornata(giornata.getId())
        );
    }

    private GaraSnapshotDTO mapGara(Gara gara) {
        return new GaraSnapshotDTO(
                gara.getId(),
                gara.getNome(),
                gara.getStato(),
                gara.getDataInizio(),
                gara.getDataFine()
        );
    }

    private GiornoGaraSnapshotDTO mapGiornata(GiornoGara giornata) {
        return new GiornoGaraSnapshotDTO(
                giornata.getId(),
                giornata.getData()
        );
    }

    private StazioneSnapshotDTO mapPostazione(Stazione postazione) {
        return new StazioneSnapshotDTO(
                postazione.getId(),
                postazione.getNome(),
                postazione.getOrdine(),
                postazione.getTipo()
        );
    }

    private PilotaSnapshotDTO mapPilota(Pilota competitor) {
        return new PilotaSnapshotDTO(
                competitor.getId(),
                competitor.getNomePilota(),
                competitor.getNumeroGara()
        );
    }

    private TempoSnapshotDTO mapTempo(TimeRecord tempo) {
        return new TempoSnapshotDTO(
                tempo.getId(),
                tempo.getPilota().getId(),
                tempo.getPilota().getId(),
                tempo.getStazione().getOrdine(),
                tempo.getTimestampRilevato()
        );
    }


}

