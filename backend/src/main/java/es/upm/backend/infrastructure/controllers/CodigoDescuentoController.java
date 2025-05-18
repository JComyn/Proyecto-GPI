package es.upm.backend.infrastructure.controllers;


import es.upm.backend.application.dto.CreateCodigoDescuentoDto;
import es.upm.backend.application.dto.CreateOficinaDto;
import es.upm.backend.application.dto.ListaCodigosDescuento;
import es.upm.backend.application.services.CodigoDescuentoService;
import es.upm.backend.domain.entities.CodigoDescuento;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.infrastructure.mapper.CodigoDescuentoMapper;
import es.upm.backend.infrastructure.mapper.OficinaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codigosDescuento")
@Tag(name = "API de Codigos de Descuento", description = "Operaciones CRUD y consultas sencillas sobre entidades Codigos de Descuento")
public class CodigoDescuentoController {
    private final CodigoDescuentoService codigoDescuentoService;
    public CodigoDescuentoController(CodigoDescuentoService codigoDescuentoService) {
        this.codigoDescuentoService = codigoDescuentoService;
    }

    @Operation(
            summary = "Recupera todos los codigos de descuento",
            description = "Recupera todos los coches de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Codigos recuperados correctamente"
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron codigos en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<ListaCodigosDescuento> findAll(){
        List<CodigoDescuento> codigosDescuento = codigoDescuentoService.findAll();
        return ResponseEntity.ok(new ListaCodigosDescuento(codigosDescuento));
    }

    @Operation(
            summary = "Crea un nuevo codigo de descuento",
            description = "Crea una nueva codigo en la base de datos comprobando que no existe otro con el mismo identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina creada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "El codigo ya esta registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<CodigoDescuento> create(@RequestBody CreateCodigoDescuentoDto newCodigo){
        // Capturar excepcion de OficinaAlreadyExistsException -> Codigo 400
        CodigoDescuento codigo = codigoDescuentoService.create(CodigoDescuentoMapper.createDto2Entity(newCodigo));
        return ResponseEntity.ok(codigo);
    }

    @Operation(
            summary = "Comprueba si existe un código de descuento",
            description = "Devuelve true si el código de descuento existe en la base de datos, false en caso contrario"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resultado de la comprobación (true = existe, false = no existe)"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("exists/{idCodigoDescuento}")
    public ResponseEntity<Boolean> existsByCodigo(@PathVariable String idCodigoDescuento){
        return ResponseEntity.ok(codigoDescuentoService.existsByCodigo(idCodigoDescuento));
    }

    @Operation(
            summary = "Devuelve el CodigoDescuento",
            description = "Devuelve el CodigoDescuento asociado al idCodigoDescuento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recupera el CodigoDescuento correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El codigo de descuento no existe"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping({"/{idCodigoDescuento}"})
    public ResponseEntity<CodigoDescuento> findById(@PathVariable String idCodigoDescuento){
        CodigoDescuento codigoDescuento = codigoDescuentoService.findByCodigo(idCodigoDescuento);
        return ResponseEntity.ok(codigoDescuento);
    }


    @Operation(
            summary = "Elimina un código de descuento",
            description = "Elimina el código de descuento asociado al idCodigoDescuento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Código de descuento eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El código de descuento no existe"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @DeleteMapping("/{idCodigoDescuento}")
    public ResponseEntity<Void> delete(@PathVariable String idCodigoDescuento) {
        codigoDescuentoService.delete(idCodigoDescuento);
        return ResponseEntity.ok().build();
    }

}
