package es.upm.backend.application.exception;

public class ReservasEmptyException extends RuntimeException{
    public ReservasEmptyException(String message) {
        super(message);
    }

    public ReservasEmptyException() {
        super("No hay reservas en la base de datos.");
    }
}
