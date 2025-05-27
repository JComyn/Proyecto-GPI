package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.application.exception.OficinaNotFoundException;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.domain.repository.OficinaRepository;
import es.upm.backend.infrastructure.repository.jpa.OficinaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OficinaRepositoryImpl implements OficinaRepository {

    private final OficinaJpaRepository oficinaJpaRepository;

    public OficinaRepositoryImpl(OficinaJpaRepository oficinaJpaRepository) {
        this.oficinaJpaRepository = oficinaJpaRepository;
    }

    @Override
    public List<Oficina> findAll() {
        List<Oficina> oficinas = oficinaJpaRepository.findAll();
        return oficinas;
    }

    @Override
    public Oficina create(Oficina oficina) {
        return oficinaJpaRepository.save(oficina);
    }

    @Override
    public void delete(Long idOficina) {
        if (!oficinaJpaRepository.existsById(idOficina)) {
            throw new OficinaNotFoundException("Oficina con id " + idOficina + " no encontrada");
        }
        oficinaJpaRepository.deleteById(idOficina);
    }

    @Override
    public boolean existsByDireccion(String direccion) {
        return oficinaJpaRepository.existsByDireccion(direccion);
    }

    @Override
    public Oficina findById(Long id) {
        return oficinaJpaRepository.findById(id).get();
    }

    @Override
    public Long getIdByDireccion(String direccion) {
        Oficina oficina = oficinaJpaRepository.findByDireccion(direccion);
        return oficina.getId();
    }

    @Override
    public Oficina save(Oficina newOficina) {
        return oficinaJpaRepository.save(newOficina);
    }
}
