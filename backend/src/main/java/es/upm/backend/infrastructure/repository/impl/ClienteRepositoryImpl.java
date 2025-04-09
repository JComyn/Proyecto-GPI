package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.domain.entities.Cliente;
import es.upm.backend.domain.repository.ClienteRepository;
import es.upm.backend.infrastructure.repository.jpa.ClienteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository){
        this.clienteJpaRepository = clienteJpaRepository;
    }


    @Override
    public List<Cliente> findAll() {
        return clienteJpaRepository.findAll();
    }

    @Override
    public Cliente create(Cliente cliente) {
        return clienteJpaRepository.save(cliente);
    }

    @Override
    public void delete(Long idCliente) {
        Optional<Cliente> clienteAEliminar = clienteJpaRepository.findById(idCliente);
        clienteJpaRepository.delete(clienteAEliminar.get());
    }
}
