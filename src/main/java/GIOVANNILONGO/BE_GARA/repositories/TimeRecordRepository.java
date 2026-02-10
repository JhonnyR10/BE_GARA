package GIOVANNILONGO.BE_GARA.repositories;

import GIOVANNILONGO.BE_GARA.entities.TimeRecord;
import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {

    boolean existsByGiornoGaraIdAndPilotaIdAndStazioneId(Long giornoGaraId, Long pilotaId, Long stazioneId);

    boolean existsByGiornoGaraIdAndPilotaId(Long giornoGaraId, Long pilotaId);

    boolean existsByGiornoGaraIdAndStazioneTipo(Long giornoGaraId, TipoStazione tipoStazione);

    List<TimeRecord> findByGiornoGaraIdAndPilotaIdOrderByStazioneOrdineAsc(Long giornoGaraId, Long pilotaId);

    List<TimeRecord> findByGiornoGaraIdOrderByStazioneOrdineAsc(Long giornoGaraId);

    List<TimeRecord> findByGiornoGaraId(Long giornoGaraId);

    long countByGiornoGaraIdAndStazioneTipo(
            Long giornoGaraId,
            TipoStazione tipo
    );


}
