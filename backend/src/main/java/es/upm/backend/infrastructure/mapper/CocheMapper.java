package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateCocheDto;
import es.upm.backend.domain.entities.CategoriaCoche;
import es.upm.backend.domain.entities.Coche;

public class CocheMapper {

    public static Coche createDto2Entity(CreateCocheDto newCoche){
        return new Coche(
                null, // id (auto-generated)
                newCoche.modelo(),
                newCoche.marca(),
                newCoche.transmision(),
                CategoriaCoche.valueOf(newCoche.categoria().toUpperCase()), // Convert String from DTO to CategoriaCoche enum, ensuring uppercase
                newCoche.puertas(),
                newCoche.techoSolar(),
                newCoche.extras(),
                newCoche.tarifaDiaria(),   // Add tarifaDiaria from DTO
                newCoche.tarifaSemanal(),  // Add tarifaSemanal from DTO
                newCoche.tarifaMensual(),  // Add tarifaMensual from DTO
                null                       // oficina (pass null as CreateCocheDto doesn't contain office info directly)
        );
    }
}
