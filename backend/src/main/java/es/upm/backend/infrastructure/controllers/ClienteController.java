package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateClienteDto;
import es.upm.backend.application.dto.ListaClientes;
import es.upm.backend.application.services.ClienteService;
import es.upm.backend.domain.entities.Cliente;
import es.upm.backend.infrastructure.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(
            summary = "Recupera todos los clientes",
            description = "Recupera todos los clientes de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes recuperados correctamente"
            ),
            @ApiResponse(responseCode = "204", description = "No hay clientes disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<ListaClientes> findAll(){
        // Capturar excepcion de ClientesEmptyException -> Codigo 204
        return ResponseEntity.ok(new ListaClientes(clienteService.findAll()));
    }


    @Operation(
            summary = "Elimina un cliente existente",
            description = "Elimina a un cliente de la base de datos utilizando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario eliminado correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "El usuario no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idCliente){
        // Capturar excepcion de ClienteNotFoundException -> codigo 400
        clienteService.delete(idCliente);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Crear un nuevo cliente",
            description = "Crea un nuevo cliente si el correo electrónico no está ya registrado en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario creado correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya está registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<Cliente> create(CreateClienteDto newCliente){
        // Capturar expcepcion de ClienteAlreadyExistsException -> codigo 400
        return ResponseEntity.ok(clienteService.create(ClienteMapper.createDto2Entity(newCliente)));
    }
}
