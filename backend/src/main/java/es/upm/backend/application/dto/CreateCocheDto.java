package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Tarifa;
import es.upm.backend.domain.entities.TipoTarifa;
import es.upm.backend.domain.entities.Transmision;

import java.util.List;

public record CreateCocheDto(
    String modelo,
    String marca,
    Transmision transmision,
    String categoria,
    short puertas,
    boolean techoSolar,
    List<String> extras,
    Double tarifaDiaria,
    Double tarifaSemanal,
    Double tarifaMensual
) {
}
