package es.upm.backend.application.services;

import es.upm.backend.application.exception.CodigoDescuentoAlreadyExistsException;
import es.upm.backend.application.exception.CodigoDescuentoNotFoundException;
import es.upm.backend.application.exception.CodigosDescuentoEmptyException;
import es.upm.backend.domain.entities.CodigoDescuento;
import es.upm.backend.domain.repository.CodigoDescuentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodigoDescuentoService {

    private final CodigoDescuentoRepository codigoDescuentoRepository;

    public CodigoDescuentoService(CodigoDescuentoRepository codigoDescuentoRepository) {
        this.codigoDescuentoRepository = codigoDescuentoRepository;
    }

    public List<CodigoDescuento> findAll() {
        List<CodigoDescuento> codigosDescuento = codigoDescuentoRepository.findAll();
        if (codigosDescuento.isEmpty()) {
            throw new CodigosDescuentoEmptyException("No se encontraron códigos de descuento disponibles.");
        }
        return codigoDescuentoRepository.findAll();
    }

    public boolean existsByCodigo(String idCodigoDescuento) {
        return codigoDescuentoRepository.existsByCogido(idCodigoDescuento);
    }

    public CodigoDescuento create(CodigoDescuento codigoDescuento) {
        if (this.existsByCodigo(codigoDescuento.getCodigo())) {
            throw new CodigoDescuentoAlreadyExistsException("El código de descuento ya existe");
        }
        return codigoDescuentoRepository.create(codigoDescuento);
    }

    public void delete(String idCodigoDescuento) {
        if (!this.existsByCodigo(idCodigoDescuento)) {
            throw new CodigoDescuentoNotFoundException("El código de descuento no existe");
        }
        codigoDescuentoRepository.delete(idCodigoDescuento);
    }

    public CodigoDescuento findByCodigo(String idCodigoDescuento) {
        CodigoDescuento codigoDescuento = codigoDescuentoRepository.findByCodigo(idCodigoDescuento);
        return codigoDescuento;
    }
}
