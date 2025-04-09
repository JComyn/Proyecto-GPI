package es.upm.backend.application.dto;

import es.upm.backend.domain.entities.Cliente;

import java.util.List;

public record ListaClientes(List<Cliente> clientes) {
}
