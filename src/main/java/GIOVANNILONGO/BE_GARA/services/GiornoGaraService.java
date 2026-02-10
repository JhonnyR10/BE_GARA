package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;
import GIOVANNILONGO.BE_GARA.exceptions.DomainException;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiornoGaraService {

    private final GiornoGaraRepository giornoGaraRepository;
    private final GaraRepository garaRepository;
    private final RealtimeService realtimeService;

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

    @Transactional
    public boolean chiudiGiornataAttiva(Long garaId) {

        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() -> new DomainException("Gara non trovata"));

        if (gara.getStato() != StatoGara.ATTIVA) {
            throw new DomainException("La gara non è in corso");
        }

        GiornoGara attiva = giornoGaraRepository
                .findByGaraIdAndStato(garaId, StatoGiornata.ATTIVA)
                .orElseThrow(() -> new DomainException("Nessuna giornata attiva"));

        attiva.setStato(StatoGiornata.CONCLUSA);

        List<GiornoGara> giornate =
                giornoGaraRepository.findByGaraIdOrderByData(garaId);

        int index = giornate.indexOf(attiva);

        if (index + 1 < giornate.size()) {
            GiornoGara successiva = giornate.get(index + 1);
            successiva.setStato(StatoGiornata.ATTIVA);
            return false;
        }

        realtimeService.inviaClassificaTotale(gara.getId());
        realtimeService.inviaSnapshotGaraAttiva();


        return true;
    }
}
