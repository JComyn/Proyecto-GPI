package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateClienteDto;
import es.upm.backend.application.dto.ListaClientes;
import es.upm.backend.application.services.ClienteService;
import es.upm.backend.domain.entities.Cliente;
import es.upm.backend.infrastructure.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "API de Clientes", description = "CRUD basico para entidades Cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<ListaClientes> findAll(){
        return ResponseEntity.ok(new ListaClientes(clienteService.findAll()));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idCliente){
        clienteService.delete(idCliente);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Cliente> create(CreateClienteDto newCliente){
        return ResponseEntity.ok(clienteService.create(ClienteMapper.createDto2Entity(newCliente)));
    }
}
