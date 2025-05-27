package es.upm.backend.application.exception;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException(String email) {
        super("Cliente con correo " + email + " ya existe.");
    }
}
