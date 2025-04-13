package es.upm.backend.application.exception;

public class ClientesEmptyException extends RuntimeException{
    public ClientesEmptyException(String message) {
        super(message);
    }

    public ClientesEmptyException() {
        super("No hay clientes en la base de datos.");
    }
}
