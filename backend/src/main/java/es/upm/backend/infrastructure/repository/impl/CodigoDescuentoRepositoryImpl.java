package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.domain.entities.CodigoDescuento;
import es.upm.backend.domain.repository.CodigoDescuentoRepository;
import es.upm.backend.infrastructure.repository.jpa.CodigoDescuentoJpaRepository;
import es.upm.backend.infrastructure.repository.jpa.OficinaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CodigoDescuentoRepositoryImpl implements CodigoDescuentoRepository {

    private final CodigoDescuentoJpaRepository codigoDescuentoJpaRepository;

    public CodigoDescuentoRepositoryImpl(CodigoDescuentoJpaRepository codigoDescuentoJpaRepository) {
        this.codigoDescuentoJpaRepository = codigoDescuentoJpaRepository;
    }

    public List<CodigoDescuento> findAll() {
        return codigoDescuentoJpaRepository.findAll();
    }
    public CodigoDescuento create(CodigoDescuento codigoDescuento) {
        return codigoDescuentoJpaRepository.save(codigoDescuento);
    }

    public CodigoDescuento findByCodigo(String idCodigoDescuento) {
        return codigoDescuentoJpaRepository.findByCodigo(idCodigoDescuento);
    }
    public boolean existsByCogido(String idCodigoDescuento) {
        return codigoDescuentoJpaRepository.existsById(idCodigoDescuento);
    }
    public void delete(String idCodigoDescuento) {
        codigoDescuentoJpaRepository.deleteById(idCodigoDescuento);
    }
    public CodigoDescuento save(CodigoDescuento codigoDescuento) {
        return codigoDescuentoJpaRepository.save(codigoDescuento);
    }
}
