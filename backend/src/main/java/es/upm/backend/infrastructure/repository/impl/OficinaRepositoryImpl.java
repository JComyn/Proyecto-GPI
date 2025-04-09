package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.application.exception.OficinaNotFoundException;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.domain.repository.OficinaRepository;
import es.upm.backend.infrastructure.repository.jpa.OficinaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OficinaRepositoryImpl implements OficinaRepository {

    private final OficinaJpaRepository oficinaJpaRepository;

    public OficinaRepositoryImpl(OficinaJpaRepository oficinaJpaRepository){
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
        Optional<Oficina> oficina = oficinaJpaRepository.findById(idOficina);
        if(oficina.isEmpty()) throw new OficinaNotFoundException(String.valueOf(idOficina));
        else oficinaJpaRepository.delete(oficina.get());
    }
}
