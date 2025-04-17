package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.exception.ReservaNotFoundException;
import es.upm.backend.application.exception.ReservasEmptyException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ReservaExceptionHandler {

    @ExceptionHandler(ReservaInvalidaException.class)
    public ResponseEntity<ErrorMessage> handleReservaInvalidaException(ReservaInvalidaException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "La reserva no cumple con las condiciones.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleReservaNotFoundException(ReservaNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "La reserva especificada no existe en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservasEmptyException.class)
    public ResponseEntity<ErrorMessage> handleReservasEmptyException(ReservasEmptyException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "No se encontraron reservas en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}