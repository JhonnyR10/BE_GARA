package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiornoGaraService {

    private final GiornoGaraRepository giornoGaraRepository;
    private final GaraRepository garaRepository;

    public List<GiornoGara> getByGara(Long garaId) {
        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() ->
                        new RuntimeException("Gara non trovata")
                );
        if (gara.getStato() == StatoGara.BOZZA) {
            throw new IllegalStateException(
                    "Le giornata sono disponibili solo per gare ATTIVE o CONCLUSE"
            );
        }
        return giornoGaraRepository.findByGaraIdOrderByNumeroAsc(gara.getId());
    }
}
