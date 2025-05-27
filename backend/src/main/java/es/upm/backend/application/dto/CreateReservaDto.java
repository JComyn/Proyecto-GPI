package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Estado;
import es.upm.backend.domain.entities.TipoTarifa;

import java.time.LocalDateTime;


public record CreateReservaDto(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion, TipoTarifa tipoTarifa, Estado estado) {
}
