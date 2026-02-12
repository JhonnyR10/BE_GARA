package GIOVANNILONGO.BE_GARA.services;

import GIOVANNILONGO.BE_GARA.entities.Gara;
import GIOVANNILONGO.BE_GARA.entities.Pilota;
import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import GIOVANNILONGO.BE_GARA.exceptions.DomainException;
import GIOVANNILONGO.BE_GARA.payloads.CreazionePilotaRequest;
import GIOVANNILONGO.BE_GARA.payloads.PilotaDTO;
import GIOVANNILONGO.BE_GARA.payloads.UpdatePilotaRequest;
import GIOVANNILONGO.BE_GARA.repositories.GaraRepository;
import GIOVANNILONGO.BE_GARA.repositories.PilotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void deletePilota(Long pilotaId) {

        Pilota pilota = pilotaRepository.findById(pilotaId)
                .orElseThrow(() -> new DomainException("Pilota non trovato"));

        if (pilota.getGara().getStato() != StatoGara.BOZZA) {
            throw new DomainException("Eliminazione consentita solo su gara in BOZZA");
        }

        pilotaRepository.delete(pilota);
    }

    @Transactional
    public PilotaDTO updatePilota(Long pilotaId, UpdatePilotaRequest dto) {

        Pilota pilota = pilotaRepository.findById(pilotaId)
                .orElseThrow(() -> new DomainException("Pilota non trovato"));

        Gara gara = pilota.getGara();

        if (gara.getStato() != StatoGara.BOZZA) {
            throw new DomainException("Modifiche consentite solo su gara in BOZZA");
        }

        if (dto.nomePilota() != null) {
            pilota.setNomePilota(dto.nomePilota());
        }

        if (dto.numeroGara() != null) {

            boolean numeroDuplicato =
                    pilotaRepository.existsByGaraIdAndNumeroGaraAndIdNot(
                            gara.getId(),
                            dto.numeroGara(),
                            pilotaId
                    );

            if (numeroDuplicato) {
                throw new DomainException("Numero gara già esistente");
            }

            pilota.setNumeroGara(dto.numeroGara());
        }

        if (dto.nomeCopilota() != null) {
            pilota.setNomeCopilota(dto.nomeCopilota());
        }
        if (dto.team() != null) {
            pilota.setTeam(dto.team());
        }

        return new PilotaDTO(
                pilota.getId(),
                pilota.getNomePilota(),
                pilota.getNumeroGara(),
                pilota.getNomeCopilota(),
                pilota.getTeam()
        );
    }


}
