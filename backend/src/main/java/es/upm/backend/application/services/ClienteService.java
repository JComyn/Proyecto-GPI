package es.upm.backend.application.services;

import es.upm.backend.domain.entities.Cliente;
import es.upm.backend.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public void delete(Long idCliente){
        clienteRepository.delete(idCliente);
    }

    public Cliente create(Cliente cliente){
        return clienteRepository.create(cliente);
    }
}
