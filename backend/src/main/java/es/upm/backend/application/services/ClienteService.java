package es.upm.backend.application.services;

import es.upm.backend.application.exception.ClienteAlreadyExistsException;
import es.upm.backend.application.exception.ClientesEmptyException;
import es.upm.backend.application.exception.InvalidCredentialsException;
import es.upm.backend.domain.entities.Cliente;
import es.upm.backend.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        if(clienteRepository.findAll().isEmpty()) {
            throw new ClientesEmptyException("No hay clientes registrados.");
        }
        return clienteRepository.findAll();
    }

        public void delete(Long idCliente) {
        clienteRepository.delete(idCliente); // Aquí no se lanza excepción, ya que el método delete() de la interfaz ClienteRepository lo maneja
    }

    public Cliente create(Cliente newCliente) {
        if (clienteRepository.existsByEmail(newCliente.getEmail())) {
            throw new ClienteAlreadyExistsException(newCliente.getEmail());
        }
        return clienteRepository.save(newCliente);
    }

    public Cliente validateCredentials(String email, String password){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null) throw new InvalidCredentialsException("Email no registrado.");
        if (cliente.getPassword().equals(password)) return cliente;
        else throw new InvalidCredentialsException("Email o contraseña incorrectos");
    }
}
