package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateOficinaDto;
import es.upm.backend.application.dto.ListaOficinas;
import es.upm.backend.application.services.OficinaService;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.infrastructure.mapper.OficinaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficinas")
@Tag(name = "API de Oficinas", description = "CRUD basico para entidades Oficina")
public class OficinaController {

    private final OficinaService oficinaService;

    public OficinaController(OficinaService oficinaService){
        this.oficinaService = oficinaService;
    }

    @Operation(
            summary = "Recupera todas las oficinas",
            description = "Recupera todas las oficinas de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficinas recuperadas correctamente"
            ),
            @ApiResponse(responseCode = "204", description = "No hay oficinas disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<ListaOficinas> findAll(){
        // Capturar excepcion de OficinasEmptyException -> Codigo 204
        List<Oficina> oficinas = oficinaService.findAll();
        return ResponseEntity.ok(new ListaOficinas(oficinas));
    }

    @Operation(
            summary = "Elimina una oficina existente", // ¿y todos los coches tambien?
            description = "Elimina una oficina existente de la base de datos utilizando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina eliminada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "La oficina no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idOficina}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idOficina){
        // Capturar excepcion de OficinaNotFoundException -> codigo 400
        oficinaService.delete(idOficina);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Crea una nueva oficina",
            description = "Crea una nueva oficina en la base de datos comprobando que no existe otra oficina con la misma dirección"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina creada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "La direccion ya esta registrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<Oficina> create(@RequestBody CreateOficinaDto newOficina){
        // Capturar excepcion de OficinaAlreadyExistsException -> Codigo 400
        Oficina oficina = oficinaService.create(OficinaMapper.createDto2Entity(newOficina));
        return ResponseEntity.ok(oficina);
    }
}
