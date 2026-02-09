package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.GiornoGara;
import GIOVANNILONGO.BE_GARA.entities.Pilota;
import GIOVANNILONGO.BE_GARA.payloads.RigaClassificaDTO;
import GIOVANNILONGO.BE_GARA.repositories.GiornoGaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.PilotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificaService {

    private final GiornoGaraRepository giornoGaraRepository;
    private final PilotaRepository pilotaRepository;
    private final CalcoloTempiService calcoloTempiService;

    public List<RigaClassificaDTO> classificaPerGiornata(
            Long giornoGaraId
    ) {

        GiornoGara giornata = giornoGaraRepository.findById(giornoGaraId)
                .orElseThrow(() ->
                        new RuntimeException("Giornata gara non trovata")
                );

        List<Pilota> piloti =
                pilotaRepository.findByGaraId(
                        giornata.getGara().getId()
                );

        List<RigaClassificaDTO> righe = new ArrayList<>();

        for (Pilota c : piloti) {

            var risultato =
                    calcoloTempiService.calcolaPerPilota(
                            giornoGaraId,
                            c.getId()
                    );

            if (risultato.tempoTotaleMillis() != null) {
                righe.add(new RigaClassificaDTO(
                        null,
                        c.getId(),
                        c.getNumeroGara(),
                        c.getNomePilota(),
                        risultato.tempoTotaleMillis()
                ));
            }
        }

        righe.sort(
                Comparator.comparingLong(
                        RigaClassificaDTO::tempoTotaleMillis
                )
        );

        int pos = 1;
        List<RigaClassificaDTO> classifica = new ArrayList<>();

        for (RigaClassificaDTO r : righe) {
            classifica.add(new RigaClassificaDTO(
                    pos++,
                    r.pilotaId(),
                    r.numeroGara(),
                    r.nomePilota(),
                    r.tempoTotaleMillis()
            ));
        }

        return classifica;
    }
}

