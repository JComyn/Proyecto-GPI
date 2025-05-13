package es.upm.backend.application.dto;

import java.time.LocalDateTime;

public record GetCochesDisponiblesDto(Long idOficinaRecogida, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion) {
}
