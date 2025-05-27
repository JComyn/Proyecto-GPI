package es.upm.backend.application.services;

import es.upm.backend.application.exception.CochesEmptyException;
import es.upm.backend.application.exception.OficinaNotFoundException;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.domain.repository.CocheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocheService {

    private final CocheRepository cocheRepository;

    public CocheService(CocheRepository cocheRepository){
        this.cocheRepository = cocheRepository;
    }

    public List<Coche> findAll() {
        List<Coche> coches = cocheRepository.findAll();
        if (coches.isEmpty()) {
            throw new CochesEmptyException();
        }
        return coches;
    }

    public List<Coche> findAllByOficinaId(Long oficinaId){
        List<Coche> coches = cocheRepository.findAllByOficinaId(oficinaId);
        if (coches.isEmpty()) throw new OficinaNotFoundException(String.valueOf(oficinaId));
        return coches;
    }

    public Coche create(Coche coche, Long idOficina){
        return cocheRepository.create(coche, idOficina);
    }

    public void delete(Long idCoche){
        cocheRepository.delete(idCoche);
    }
}
