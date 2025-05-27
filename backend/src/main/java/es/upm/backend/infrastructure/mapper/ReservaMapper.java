package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateReservaDto;
import es.upm.backend.application.dto.RealizarReservaDto;
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
                null,
                newReserva.estado(),
                0
        );
    }

    /*public static RealizarReservaDto entityToDto(Reserva reserva) {
        return new RealizarReservaDto(
                reserva.getCoche().getId(),
                reserva.getCliente().getId(),
                reserva.getOficinaRecogida().getId(),
                reserva.getOficinaDevolucion().getId(),
                reserva.getFechaHoraRecogida(),
                reserva.getFechaHoraDevolucion(),
                reserva.getTipoTarifa()
        );
    }*/
}
