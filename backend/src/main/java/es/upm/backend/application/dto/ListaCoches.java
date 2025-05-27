package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Coche;

import java.util.List;

public record ListaCoches(List<Coche> coches) {
}
