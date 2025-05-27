package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Reserva;

import java.util.List;

public record ListaReservas(List<Reserva> reservas) {
}
