package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Reserva;

public record ReservaTarifaDto(Reserva reserva, boolean codigoAplicado,double precio) {
}
