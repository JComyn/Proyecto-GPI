package es.upm.backend.application.dto;

import java.time.LocalDateTime;

public record RealizarReservaDto(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion) {
}
