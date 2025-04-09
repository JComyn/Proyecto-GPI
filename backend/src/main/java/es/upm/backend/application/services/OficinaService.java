package es.upm.backend.application.services;

import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.domain.repository.OficinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OficinaService {

    private final OficinaRepository oficinaRepository;

    public OficinaService(OficinaRepository oficinaRepository){
        this.oficinaRepository = oficinaRepository;
    }

    public List<Oficina> findAll(){
        List<Oficina> oficinas = oficinaRepository.findAll();

        //if (oficinas.isEmpty()) throw new OficinasEmptyException();

        return oficinas;
    }

    public Oficina create(Oficina newOficina){
        Oficina oficina = oficinaRepository.create(newOficina);
        return oficina;
    }

    public void delete(Long idOficina){
        oficinaRepository.delete(idOficina);
    }
}
