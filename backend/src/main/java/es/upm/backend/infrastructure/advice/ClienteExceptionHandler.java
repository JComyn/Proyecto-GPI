package es.upm.backend.infrastructure.advice;

import es.upm.backend.application.dto.ErrorMessage;
import es.upm.backend.application.exception.ClienteAlreadyExistsException;
import es.upm.backend.application.exception.ClienteNotFoundException;
import es.upm.backend.application.exception.ClientesEmptyException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ClienteExceptionHandler {

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleClienteAlreadyExistsException(ClienteAlreadyExistsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El cliente ya está registrado en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleClienteNotFoundException(ClienteNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "El cliente especificado no existe en el sistema.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // 404 Not Found
    }

    @ExceptionHandler(ClientesEmptyException.class)
    public ResponseEntity<ErrorMessage> handleClientesEmptyException(ClientesEmptyException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                "No se encontraron clientes en la base de datos.",
                LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // 404 Not Found
    }
}
