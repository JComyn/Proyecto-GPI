package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateCocheDto;
import es.upm.backend.application.dto.ListaCoches;
import es.upm.backend.application.services.CocheService;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.infrastructure.mapper.CocheMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coches")
@Tag(name = "API de Coches", description = "Operaciones CRUD y consultas sencillas sobre entidades Coche")
public class CocheController {

    private final CocheService cocheService;

    public CocheController(CocheService cocheService){
        this.cocheService = cocheService;
    }

    @Operation(
            summary = "Recupera todos los coches",
            description = "Recupera todos los coches de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Coches recuperados correctamente"
            ),
            @ApiResponse(responseCode = "204", description = "No hay coches disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<ListaCoches> findAll(){
        // Capturar excepcion de CochesEmptyException -> Codigo 204
        List<Coche> coches = cocheService.findAll();
        return ResponseEntity.ok(new ListaCoches(coches));
    }

    @Operation(
            summary = "Recupera coches de una oficina",
            description = "Recupera todos los coches de una oficina mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Coches recuperados correctamente"
            ),
            @ApiResponse(responseCode = "204", description = "No hay coches disponibles en esta oficina"),
            @ApiResponse(responseCode = "400", description = "Oficina no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idOficina}")
    public ResponseEntity<ListaCoches> findByIdOficina(@PathVariable Long idOficina){
        // Capturar excepcion de CochesEmptyException -> Codigo 204
        //Capturar exception OficinaNotFoundException -> Codigo 400
        List<Coche> coches = cocheService.findAllByOficinaId(idOficina);
        return ResponseEntity.ok(new ListaCoches(coches));
    }

    @Operation(
            summary = "Crea un nuevo coche",
            description = "Crea un nuevo coche en la base de datos y lo asocia a una oficina mediante el idOficina"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Coche agregado correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "Oficina no existe, ¿y con newCoche?"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/{idOficina}")
    public ResponseEntity<Coche> create(@PathVariable Long idOficina, @RequestBody CreateCocheDto newCoche){
        // Capturar excepcion de OficinaNotFoundException -> Codigo 400
        // Capturar excepcion de CocheAlreadyExistsException -> Codigo 400 ??
        Coche coche = cocheService.create(CocheMapper.createDto2Entity(newCoche), idOficina);
        return ResponseEntity.ok(coche);
    }

    @Operation(
            summary = "Elimina un coche existente",
            description = "Elimina un coche de la base de datos utilizando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Coche eliminado correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "Coche no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idCoche}")
    public ResponseEntity<Void> delete(@PathVariable Long idCoche){
        //Capturar excepcion de CocheNotFoundException -> Codigo 400
        cocheService.delete(idCoche);
        return ResponseEntity.ok().build();
    }


}
