package es.upm.backend.application.dto;

import java.time.LocalDate;

public record CreateClienteDto(String nombre, String apellidos, String email, String password, String direccion, String nif, LocalDate nacimiento, boolean esNEgocio) {
}
