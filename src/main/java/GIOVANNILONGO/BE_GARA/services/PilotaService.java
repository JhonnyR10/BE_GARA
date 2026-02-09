package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.Pilota;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.payloads.CreazionePilotaRequest;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.PilotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotaService {
    private final PilotaRepository pilotaRepository;
    private final GaraRepository garaRepository;

    public Pilota create(CreazionePilotaRequest dto) {

        Gara gara = garaRepository.findById(dto.garaId())
                .orElseThrow(() ->
                        new RuntimeException("Gara non trovata")
                );
        if (gara.getStato() != StatoGara.BOZZA) {
            throw new IllegalStateException(
                    "I piloti possono essere modificati solo se la gara è in BOZZA"
            );
        }
        if (pilotaRepository.existsByGaraIdAndNumeroGara(gara.getId(), dto.numeroGara())) {
            throw new IllegalStateException("Numero già utilizzato in questa gara");
        }
        Pilota p = new Pilota();
        p.setGara(gara);
        p.setNumeroGara(dto.numeroGara());
        p.setNomePilota(dto.nomePilota());
        p.setNomeCopilota(dto.nomeCopilota());
        p.setTeam(dto.team());

        return pilotaRepository.save(p);

    }

    public List<Pilota> listByGara(Long garaId) {
        Gara gara = garaRepository.findById(garaId)
                .orElseThrow(() ->
                        new RuntimeException("Gara non trovata")
                );
        return pilotaRepository.findByGaraId(gara.getId());
    }
}
