package es.upm.backend.domain.repository;

import es.upm.backend.domain.entities.Oficina;

import java.util.List;

public interface OficinaRepository {
    List<Oficina> findAll();
    Oficina create(Oficina oficina);
    void delete(Long idOficina);
    boolean existsByDireccion(String direccion);
    Oficina findById(Long id);
    Long getIdByDireccion(String direccion);
    Oficina save(Oficina newOficina);
}
