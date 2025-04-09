package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Oficina;

import java.util.List;

public record ListaOficinas(List<Oficina> oficinas) {
}
