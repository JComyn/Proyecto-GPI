package es.upm.backend.application.services;

import es.upm.backend.application.exception.OficinaAlreadyExistsException;
import es.upm.backend.application.exception.OficinaFormatoIncorrectoException;
import es.upm.backend.application.exception.OficinasEmptyException;
import es.upm.backend.application.utils.ValidationUtils;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.domain.repository.OficinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OficinaService {

    private final OficinaRepository oficinaRepository;

    public OficinaService(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    public List<Oficina> findAll() {
        List<Oficina> oficinas = oficinaRepository.findAll();
        if (oficinas.isEmpty()) {
            throw new OficinasEmptyException("No se encontraron oficinas disponibles.");
        }
        return oficinas;
    }

    public Oficina create(Oficina newOficina) {
        if (!ValidationUtils.isFormatoValido(newOficina)) {
            throw new OficinaFormatoIncorrectoException(); 
        }
        if (oficinaRepository.existsByDireccion(newOficina.getDireccion())) {
            throw new OficinaAlreadyExistsException(newOficina.getDireccion());
        }
        return oficinaRepository.save(newOficina);
    }

    public void delete(Long idOficina) {
        oficinaRepository.delete(idOficina);
    }
}
