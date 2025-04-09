package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateReservaDto;
import es.upm.backend.application.dto.ListaReservas;
import es.upm.backend.application.dto.RealizarReservaDto;
import es.upm.backend.application.dto.ValidarReservaDto;
import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.services.ReservaService;
import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.infrastructure.mapper.ReservaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
@Tag(name = "API de Reservas", description = "Operaciones relacionadas con las reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }

    @GetMapping()
    public ResponseEntity<ListaReservas> findAll(){
        return ResponseEntity.ok(new ListaReservas(reservaService.findAll()));
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idReserva){
        reservaService.delete(idReserva);
        return ResponseEntity.ok().build();
    }

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


    @PostMapping("/realizar")
    public ResponseEntity<Reserva> realizarReserva(@RequestBody RealizarReservaDto realizarReservaDto){
        Reserva reservaRealizada = reservaService.realizarReserva(
                realizarReservaDto.idCoche(),
                realizarReservaDto.idCliente(),
                realizarReservaDto.idOficinaRecogida(),
                realizarReservaDto.idOficinaDevolucion(),
                realizarReservaDto.fechaHoraRecogida(),
                realizarReservaDto.fechaHoraDevolucion()
        );
        return ResponseEntity.ok(reservaRealizada);
    }
}
