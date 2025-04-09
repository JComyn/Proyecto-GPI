package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Transmision;

public record CreateCocheDto(String modelo, String marca, Transmision transmision, String color, short puertas, boolean techoSolar) {
}
