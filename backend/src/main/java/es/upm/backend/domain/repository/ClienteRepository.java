package es.upm.backend.domain.repository;


import es.upm.backend.domain.entities.Cliente;

import java.util.List;

public interface ClienteRepository {

    List<Cliente> findAll();
    Cliente create(Cliente cliente);
    void delete(Long idCliente);
}
