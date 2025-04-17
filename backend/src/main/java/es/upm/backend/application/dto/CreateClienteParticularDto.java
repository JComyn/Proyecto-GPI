package es.upm.backend.application.dto;

import java.time.LocalDate;

public record CreateClienteParticularDto(String nombre, String apellidos, String email, String password, String direccion, LocalDate nacimiento) {
}
