package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GaraRepository extends JpaRepository<Gara, Long> {
    @Override
    Optional<Gara> findById(Long aLong);

    Optional<Gara> findByStato(StatoGara stato);

    default Optional<Gara> findGaraAttiva() {
        return findByStato(StatoGara.ATTIVA);
    }

}
