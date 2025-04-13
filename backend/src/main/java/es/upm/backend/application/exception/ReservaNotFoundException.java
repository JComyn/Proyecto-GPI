package es.upm.backend.application.exception;

public class ReservaNotFoundException extends RuntimeException{
    public ReservaNotFoundException(String message) {
        super(message);
    }

    public ReservaNotFoundException(Long idReserva) {
        super("Reserva con id " + idReserva + " no encontrada.");
    }
}
