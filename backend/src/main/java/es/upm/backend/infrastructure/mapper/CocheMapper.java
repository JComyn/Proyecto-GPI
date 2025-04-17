package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateCocheDto;
import es.upm.backend.domain.entities.Coche;

public class CocheMapper {

    public static Coche createDto2Entity(CreateCocheDto newCoche){
        return new Coche(
                null,
                newCoche.modelo(),
                newCoche.marca(),
                newCoche.transmision(),
                newCoche.categoria(),
                newCoche.puertas(),
                newCoche.techoSolar(),
                newCoche.extras(),
                newCoche.tarifa(),
                null
        );
    }
}
