package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.CodigoDescuento;
import es.upm.backend.domain.entities.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoDescuentoJpaRepository extends JpaRepository<CodigoDescuento, String> {
    CodigoDescuento findByCodigo(String idCodigoDescuento);
    boolean existsByCodigo(String idCodigoDescuento);

}
