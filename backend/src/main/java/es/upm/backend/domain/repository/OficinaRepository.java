package es.upm.backend.domain.repository;

import es.upm.backend.domain.entities.Oficina;

import java.util.List;

public interface OficinaRepository {
    List<Oficina> findAll();
    Oficina create(Oficina oficina);
    void delete(Long idOficina);
}
