package es.upm.backend.application.exception;

public class OficinaNotFoundException extends RuntimeException{
    // idOficina podria ser un int
    public OficinaNotFoundException(String idOficina) {
        super("Oficina con id " + idOficina + " no encontrada");
    }
}
