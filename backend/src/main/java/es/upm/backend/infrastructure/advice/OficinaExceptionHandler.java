package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.OficinaAlreadyExistsException;
import es.upm.backend.application.exception.OficinaFormatoIncorrectoException;
import es.upm.backend.application.exception.OficinaNotFoundException;
import es.upm.backend.application.exception.OficinasEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OficinaExceptionHandler {

    @ExceptionHandler(OficinasEmptyException.class)
    public ResponseEntity<ErrorMessage> handleOficinasEmptyException(OficinasEmptyException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "No se encontraron oficinas en la base de datos.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OficinaAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleOficinaAlreadyExistsException(OficinaAlreadyExistsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "La oficina ya está registrada en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OficinaFormatoIncorrectoException.class)
    public ResponseEntity<ErrorMessage> handleOficinaFormatoIncorrectoException(OficinaFormatoIncorrectoException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El formato de los datos de la oficina es inválido.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OficinaNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleOficinaNotFoundException(OficinaNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "La oficina especificada no existe en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}