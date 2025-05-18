package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.*;
import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.services.ReservaService;
import es.upm.backend.application.services.TarifaService;
import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.domain.entities.Tarifa;
import es.upm.backend.infrastructure.mapper.ReservaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
@Tag(name = "API de Reservas", description = "Operaciones relacionadas con las reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final TarifaService tarifaService;

    public ReservaController(ReservaService reservaService, TarifaService tarifaService) {
        this.reservaService = reservaService;
        this.tarifaService = tarifaService;
    }


    @Operation(
            summary = "Recupera todas las reservas",
            description = "Recupera todas las reservas de la base de datos ¿Deberia solo las proximas o actuales?"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservas recuperadas correctamente"
            ),
            @ApiResponse(responseCode = "204", description = "No hay reservas disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<ListaReservas> findAll(){
        return ResponseEntity.ok(new ListaReservas(reservaService.findAll()));
    }

    @Operation(
            summary = "Elimina una reserva existente",
            description = "Elimina una reserva existente de la base de datos utilizando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva eliminada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "La reserva no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idReserva){
        reservaService.delete(idReserva);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Crea una nueva reserva",
            description = "Crea una nueva reserva a partide los datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva creada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "Cuerpo de la solicitud inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<Reserva> create(CreateReservaDto newReserva){
        return ResponseEntity.ok(reservaService.create(
                ReservaMapper.createDto2Entity(newReserva),
                newReserva.idCoche(),
                newReserva.idCliente(),
                newReserva.idOficinaRecogida(),
                newReserva.idOficinaDevolucion()));
    }

    @Operation(
            summary = "Validar una reserva",
            description = "Valida si una reserva puede realizarse en función de la disponibilidad del coche y su ubicación en la fecha de recogida."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resultado de la validación (true = válida, false = inválida)"
            ),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/validar")
    public ResponseEntity<Void> validarReserva(@RequestBody ValidarReservaDto validarReservaDto){

        boolean reservaValida = reservaService.reservaValida(
                validarReservaDto.idCoche(),
                validarReservaDto.idOficinaRecogida(),
                validarReservaDto.fechaHoraRecogida(),
                validarReservaDto.fechaHoraDevolucion()
        );
        if(!reservaValida) throw new ReservaInvalidaException("Reserva no valida: resultado false, metodo del Service ejecutado sin excepciones.");
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Realiza una reserva de manera efectiva",
            description = "Realiza una reserva de manera efectiva en la base de datos utilizando los datos proporcionados." +
                    " Se valida la reserva antes de realizarla y se calcula el precio final aplicando un posible descuento."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva realizada correctamente"
            ),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/realizar")
    public ResponseEntity<ReservaTarifaDto> realizarReserva(@RequestBody RealizarReservaDto realizarReservaDto){

        Reserva reservaRealizada = reservaService.realizarReserva(
                realizarReservaDto.idCoche(),
                realizarReservaDto.idCliente(),
                realizarReservaDto.idOficinaRecogida(),
                realizarReservaDto.idOficinaDevolucion(),
                realizarReservaDto.fechaHoraRecogida(),
                realizarReservaDto.fechaHoraDevolucion(),
                realizarReservaDto.tipoTarifa()
        );


        //Calcular precio final y actualizar la reserva
        String codigoDescuento = realizarReservaDto.codigoDescuento();
        double precioFinal;
        boolean descuentoAplicado = false;
        try {
            // Calcular el precio
            Pair<Double, Boolean> calculo = tarifaService.calcularPrecio(reservaRealizada, codigoDescuento);
            precioFinal = calculo.a;
            descuentoAplicado = calculo.b;
            reservaRealizada.setPrecio(precioFinal);

            // Guardar la reserva con el precio actualizado
            reservaService.save(reservaRealizada);
        } catch (ReservaInvalidaException e) {

            // Eliminar la reserva si ocurre una excepción
            reservaService.delete(reservaRealizada.getId());
            // Volver a lanzar la misma excepción para que el handler la procese
            throw e;
        }

        // Crear el DTO de respuesta
        ReservaTarifaDto tarifaReservaDto = new ReservaTarifaDto(reservaRealizada, descuentoAplicado, precioFinal);

        return ResponseEntity.ok(tarifaReservaDto);
    }

    @PostMapping("/disponibles")
    public ResponseEntity<ListaCoches> getCochesDisponibles(@RequestBody GetCochesDisponiblesDto req){
        return ResponseEntity.ok(new ListaCoches(
                reservaService.getCochesDisponibles(req.idOficinaRecogida(), req.fechaRecogida(), req.fechaDevolucion())
        ));
    }
}
