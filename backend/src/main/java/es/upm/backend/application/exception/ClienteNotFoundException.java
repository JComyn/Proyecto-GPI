package es.upm.backend.application.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException(Long idCliente) {
        super("Cliente con id " + idCliente + " no existe.");
    }
}
