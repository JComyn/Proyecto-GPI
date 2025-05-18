package es.upm.backend.domain.repository;

import es.upm.backend.domain.entities.CodigoDescuento;

import java.util.List;

public interface CodigoDescuentoRepository {
    List<CodigoDescuento> findAll(); // GET /codigoDescuento
    CodigoDescuento create(CodigoDescuento codigoDescuento); // POST /codigoDescuento
    CodigoDescuento findByCodigo(String idCodigoDescuento);
    boolean existsByCogido(String idCodigoDescuento); // GET /codigoDescuento/{idCodigoDescuento}
    void delete(String idCodigoDescuento); // DELETE /codigoDescuento/{idCodigoDescuento}
    CodigoDescuento save(CodigoDescuento codigoDescuento);

}
