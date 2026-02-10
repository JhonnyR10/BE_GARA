package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiornoGaraRepository extends JpaRepository<GiornoGara, Long> {
    List<GiornoGara> findByGaraIdOrderByNumeroAsc(Long garaId);

    List<GiornoGara> findByGaraId(Long garaId);

    Optional<GiornoGara> findByGaraIdAndStato(Long garaId, StatoGiornata stato);

    List<GiornoGara> findByGaraIdOrderByData(Long garaId);

}
