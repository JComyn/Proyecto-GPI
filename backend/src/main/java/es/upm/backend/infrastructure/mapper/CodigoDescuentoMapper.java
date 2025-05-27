package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateCocheDto;
import es.upm.backend.application.dto.CreateCodigoDescuentoDto;
import es.upm.backend.domain.entities.CategoriaCoche;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.domain.entities.CodigoDescuento;

public class CodigoDescuentoMapper {
    public static CodigoDescuento createDto2Entity(CreateCodigoDescuentoDto newCodigoDescuento) {
        return new CodigoDescuento(
                newCodigoDescuento.codigo(),
                newCodigoDescuento.descuento()
        );
    }
}