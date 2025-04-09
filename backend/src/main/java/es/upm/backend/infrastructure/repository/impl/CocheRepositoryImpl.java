package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.application.exception.CocheNotFoundException;
import es.upm.backend.application.exception.OficinaNotFoundException;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.domain.repository.CocheRepository;
import es.upm.backend.infrastructure.repository.jpa.CocheJpaRepository;
import es.upm.backend.infrastructure.repository.jpa.OficinaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CocheRepositoryImpl implements CocheRepository {

    private final CocheJpaRepository cocheJpaRepository;
    private final OficinaJpaRepository oficinaJpaRepository;

    public CocheRepositoryImpl(CocheJpaRepository cocheJpaRepository, OficinaJpaRepository oficinaJpaRepository){
        this.cocheJpaRepository = cocheJpaRepository;
        this.oficinaJpaRepository = oficinaJpaRepository;
    }


    @Override
    public List<Coche> findAll() {
        List<Coche> coches = cocheJpaRepository.findAll();
        return coches;
    }

    @Override
    public List<Coche> findAllByOficinaId(Long oficinaId) {

        List<Coche> coches = cocheJpaRepository.findAllByOficinaId(oficinaId);
        return coches;

    }

    @Override
    public Coche create(Coche coche, Long idOficina) {
        Optional<Oficina> optionalOficina = oficinaJpaRepository.findById(idOficina);
        if (optionalOficina.isEmpty()) throw new OficinaNotFoundException(String.valueOf(idOficina));
        Oficina oficina = optionalOficina.get();
        oficina.agregarCoche(coche);
        Coche newCoche = cocheJpaRepository.save(coche);
        return newCoche;
    }

    @Override
    public void delete(Long idCoche) {
        Optional<Coche> coche = cocheJpaRepository.findById(idCoche);
        if (coche.isEmpty()) throw new CocheNotFoundException("Coche con id " + idCoche + "no encontrado.");
        else cocheJpaRepository.delete(coche.get());
    }
}
