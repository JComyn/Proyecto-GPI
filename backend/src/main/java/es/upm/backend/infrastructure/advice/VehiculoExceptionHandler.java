package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.VehiculoNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class VehiculoExceptionHandler {

    @ExceptionHandler(VehiculoNoDisponibleException.class)
    public ResponseEntity<ErrorMessage> handleVehiculoNoDisponibleException(VehiculoNoDisponibleException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El vehículo no está disponible en las fechas seleccionadas.",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}