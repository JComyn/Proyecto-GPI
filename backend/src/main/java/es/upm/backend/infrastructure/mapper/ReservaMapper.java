package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateReservaDto;
import es.upm.backend.domain.entities.Reserva;

public class ReservaMapper {

    public static Reserva createDto2Entity(CreateReservaDto newReserva){
        return new Reserva(
                null,
                null,
                null,
                null,
                null,
                newReserva.fechaHoraRecogida(),
                newReserva.fechaHoraDevolucion(),
                newReserva.estado()
        );
    }
}
