package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.Pilota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PilotaRepository extends JpaRepository<Pilota, Long> {
    List<Pilota> findByGaraId(Long garaId);

    boolean existsByGaraIdAndNumeroGara(Long garaId, String numeroGara);

    long countByGaraId(Long garaId);


}
