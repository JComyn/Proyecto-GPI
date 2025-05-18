package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CodigoDescuentoExceptionHandler {

    @ExceptionHandler(CodigosDescuentoEmptyException.class)
    public ResponseEntity<ErrorMessage> handleCodigosDescuentoEmptyException(CodigosDescuentoEmptyException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "No se encontraron codigos de descuento en la base de datos.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodigoDescuentoAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleCodigoDescuentoAlreadyExistsException(CodigoDescuentoAlreadyExistsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El codigo de descuento debe ser unico,y ya está registrada en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CodigoDescuentoNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCodigoDescuentoNotFoundException(CodigoDescuentoNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El codigo de descuento especificado no existe en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
