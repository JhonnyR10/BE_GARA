package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiornoGaraRepository extends JpaRepository<GiornoGara, Long> {
    List<GiornoGara> findByGaraIdOrderByNumeroAsc(Long garaId);

    Optional<GiornoGara> findByGaraId(Long garaId);

}
