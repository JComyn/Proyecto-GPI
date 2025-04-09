package es.upm.backend.application.exception;

// Esta a lo mejor sobra
public class OficinaFormatoIncorrectoException extends RuntimeException {
    public OficinaFormatoIncorrectoException() {
        super("El formato de la oficina es incorrecto");
    }
}
