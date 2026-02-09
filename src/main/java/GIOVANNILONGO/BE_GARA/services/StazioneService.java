package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.Stazione;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.StazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StazioneService {
    private final StazioneRepository stazioneRepository;
    private final GaraRepository garaRepository;

    public List<Stazione> listByGara(long garaId) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new RuntimeException("Gara non trovata"));
        if (gara.getStato() == StatoGara.BOZZA) {
            throw new IllegalStateException(
                    "Le Postazioni sono disponibili solo dopo l'apertura della gara"
            );
        }
        return stazioneRepository.findByGaraIdOrderByOrdineAsc(garaId);
    }

    private String generaApiKey() {
        return UUID.randomUUID().toString();
    }

}
