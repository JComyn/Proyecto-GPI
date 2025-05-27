package es.upm.backend.application.exception;

public class CochesEmptyException extends RuntimeException{
    public CochesEmptyException(String message) {
        super(message);
    }

    public CochesEmptyException() {
        super("No hay coches en la base de datos.");
    }
}
