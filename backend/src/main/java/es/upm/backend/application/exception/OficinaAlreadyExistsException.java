package es.upm.backend.application.exception;

public class OficinaAlreadyExistsException extends RuntimeException {
    public OficinaAlreadyExistsException(String direccionOficina) {
        super("La direccion " + direccionOficina + " ya esta registrada");
    }
}
