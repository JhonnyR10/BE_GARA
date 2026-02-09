package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.Stazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StazioneRepository extends JpaRepository<Stazione, Long> {
    List<Stazione> findByGaraIdOrderByOrdineAsc(Long garaId);

    Optional<Stazione> findByGaraIdAndOrdine(Long garaId, Integer ordine);

    List<Stazione> findByGaraIdOrderByOrdine(Long garaId);

}
