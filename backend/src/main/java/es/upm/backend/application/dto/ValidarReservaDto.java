package es.upm.backend.application.dto;

import java.time.LocalDateTime;

public record ValidarReservaDto(Long idCoche, Long idOficinaRecogida, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion) {
}
