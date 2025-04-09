package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateOficinaDto;
import es.upm.backend.domain.entities.Oficina;

import java.util.ArrayList;
import java.util.List;

public class OficinaMapper {

    public static Oficina createDto2Entity(CreateOficinaDto newOficina){
        return new Oficina(
                null,
                newOficina.direccion(),
                newOficina.telefono(),
                new ArrayList<>()
        );
    }
}
