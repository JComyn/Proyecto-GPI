package es.upm.backend.domain.repository;


import es.upm.backend.domain.entities.Cliente;

import java.util.List;

public interface ClienteRepository {
    List<Cliente> findAll();
    Cliente create(Cliente cliente);
    void delete(Long idCliente);
    boolean existsByEmail(String email);
    Cliente save(Cliente cliente);
    boolean existsById(Long idCliente);
    Cliente findByEmail(String email);
}
