package es.upm.backend.application.exception;

public class ReservaInvalidaException extends RuntimeException {
    public ReservaInvalidaException() {
        super("La reserva no es válida");
    }

    public ReservaInvalidaException(String motivo) {
        super("La reserva no es válida: " + motivo);
    }
}
