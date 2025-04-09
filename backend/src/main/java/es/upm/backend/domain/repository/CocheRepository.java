package es.upm.backend.domain.repository;

import es.upm.backend.domain.entities.Coche;

import java.util.List;
import java.util.Optional;

public interface CocheRepository {
    List<Coche> findAll();
    List<Coche> findAllByOficinaId(Long oficinaId);
    Coche create(Coche coche, Long idOficina);
    void delete(Long idCoche);
}
