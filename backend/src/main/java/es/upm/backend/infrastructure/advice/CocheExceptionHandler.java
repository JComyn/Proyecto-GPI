package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.CocheNotFoundException;
import es.upm.backend.application.exception.CochesEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CocheExceptionHandler {

    @ExceptionHandler(CocheNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCocheNotFoundException(CocheNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El coche especificado no existe en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CochesEmptyException.class)
    public ResponseEntity<ErrorMessage> handleCochesEmptyException(CochesEmptyException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "No hay coches disponibles en la base de datos.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}