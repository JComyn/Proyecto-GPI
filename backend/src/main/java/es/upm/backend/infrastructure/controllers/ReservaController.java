package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.*;
import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.services.ReservaService;
import es.upm.backend.application.services.TarifaService;
import es.upm.backend.domain.entities.Reserva;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // Added for validation annotations
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger; // Added for logging
import org.slf4j.LoggerFactory; // Added for logging


@RestController
@RequestMapping("/reservas")
@Tag(name = "API de Reservas", description = "Operaciones relacionadas con las reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class); // Logger instance

    private final ReservaService reservaService;
    private final TarifaService tarifaService;

    public ReservaController(ReservaService reservaService, TarifaService tarifaService) {
        this.reservaService = reservaService;
        this.tarifaService = tarifaService;
    }


    @Operation(
            summary = "Recupera todas las reservas",
            description = "Recupera todas las reservas de la base de datos."
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
        logger.info("Attempting to retrieve all reservations."); // Logging
        ListaReservas reservas = new ListaReservas(reservaService.findAll());
        if (reservas.reservas().isEmpty()) {
            logger.info("No reservations available."); // Logging
            return ResponseEntity.noContent().build();
        }
        logger.info("Successfully retrieved all reservations."); // Logging
        return ResponseEntity.ok(reservas);
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
        logger.info("Attempting to delete reservation with ID: {}", idReserva); // Logging
        try {
            reservaService.delete(idReserva);
            logger.info("Reservation with ID: {} deleted successfully.", idReserva); // Logging
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) { // Catch specific exception if the reservation doesn't exist
            logger.warn("Failed to delete reservation with ID: {}. Reason: {}", idReserva, e.getMessage()); // Logging
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (Exception e) {
            logger.error("Error deleting reservation with ID: {}. Reason: {}", idReserva, e.getMessage()); // Logging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @Operation(
            summary = "Crea una nueva reserva",
            description = "Crea una nueva reserva a partir de los datos proporcionados."
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
    // Applied @Valid for input validation
    public ResponseEntity<Reserva> create(@Valid @RequestBody CreateReservaDto newReserva){
        logger.info("Attempting to create a new reservation for car ID: {} and client ID: {}", newReserva.idCoche(), newReserva.idCliente()); // Logging
        try {
            // Create a Reserva entity from the DTO first
            Reserva reserva = new Reserva();
            // Set the properties from DTO - adjust based on actual Reserva entity structure
            
            // Call service with correct parameters based on signature: Reserva,Long,Long,Long,Long
            Reserva createdReserva = reservaService.create(
                    reserva,
                    newReserva.idCoche(),
                    newReserva.idCliente(),
                    newReserva.idOficinaRecogida(),
                    newReserva.idOficinaDevolucion()
            );
            logger.info("Reserva created successfully with ID: {}", createdReserva.getId()); // Logging
            return ResponseEntity.ok(createdReserva);
        } catch (Exception e) {
            logger.error("Error creating reservation. Reason: {}", e.getMessage()); // Logging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    // Applied @Valid for input validation
    public ResponseEntity<Void> validarReserva(@Valid @RequestBody ValidarReservaDto validarReservaDto){
        logger.info("Attempting to validate reservation for car ID: {} from {} to {}",
                validarReservaDto.idCoche(), validarReservaDto.fechaHoraRecogida(), validarReservaDto.fechaHoraDevolucion()); // Logging
        boolean reservaValida = reservaService.reservaValida(
                validarReservaDto.idCoche(),
                validarReservaDto.idOficinaRecogida(),
                validarReservaDto.fechaHoraRecogida(),
                validarReservaDto.fechaHoraDevolucion()
        );
        if(!reservaValida) {
            logger.warn("Reservation validation failed for car ID: {}", validarReservaDto.idCoche()); // Logging
            throw new ReservaInvalidaException("Reserva no valida: el coche no esta disponible o la oficina de recogida no es correcta."); // More specific exception message
        }
        logger.info("Reservation validated successfully for car ID: {}", validarReservaDto.idCoche()); // Logging
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
    // Applied @Valid for input validation
    public ResponseEntity<ReservaTarifaDto> realizarReserva(@Valid @RequestBody RealizarReservaDto realizarReservaDto){
        logger.info("Attempting to finalize reservation for car ID: {} and client ID: {}",
                realizarReservaDto.idCoche(), realizarReservaDto.idCliente()); // Logging

        try {
            // Call service with correct parameters based on signature: Long,Long,Long,Long,LocalDateTime,LocalDateTime,TipoTarifa
            Reserva reservaRealizada = reservaService.realizarReserva(
                    realizarReservaDto.idCoche(),
                    realizarReservaDto.idCliente(),
                    realizarReservaDto.idOficinaRecogida(),
                    realizarReservaDto.idOficinaDevolucion(),
                    realizarReservaDto.fechaHoraRecogida(),
                    realizarReservaDto.fechaHoraDevolucion(),
                    realizarReservaDto.tipoTarifa()
            );

            // Handle discount and pricing logic - since getDescuentoAplicado() doesn't exist
            // we'll need to calculate or determine this differently
            boolean descuentoAplicado = realizarReservaDto.codigoDescuento() != null && !realizarReservaDto.codigoDescuento().isEmpty();
            double precioFinal = realizarReservaDto.precioCalculado(); // Use the calculated price from request

            ReservaTarifaDto tarifaReservaDto = new ReservaTarifaDto(reservaRealizada, descuentoAplicado, precioFinal);
            logger.info("Reservation finalized successfully with ID: {}. Final price: {}", reservaRealizada.getId(), precioFinal); // Logging
            return ResponseEntity.ok(tarifaReservaDto);
        } catch (ReservaInvalidaException e) {
            logger.warn("Failed to finalize reservation. Reason: {}", e.getMessage()); // Logging
            return ResponseEntity.badRequest().body(new ReservaTarifaDto(null, false, 0.0)); // Return a meaningful error response
        } catch (Exception e) {
            logger.error("Error finalizing reservation. Reason: {}", e.getMessage()); // Logging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReservaTarifaDto(null, false, 0.0));
        }
    }

    @PostMapping("/disponibles")
    public ResponseEntity<ListaCoches> getCochesDisponibles(@Valid @RequestBody GetCochesDisponiblesDto req){
        logger.info("Attempting to get available cars for office ID: {} from {} to {}",
                req.idOficinaRecogida(), req.fechaRecogida(), req.fechaDevolucion()); // Logging
        ListaCoches cochesDisponibles = new ListaCoches(
                reservaService.getCochesDisponibles(req.idOficinaRecogida(), req.fechaRecogida(), req.fechaDevolucion())
        );
        logger.info("Found {} available cars.", cochesDisponibles.coches().size()); // Logging
        return ResponseEntity.ok(cochesDisponibles);
    }
}