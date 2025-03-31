package es.upm.backend.application.exception;

// Esta a lo mejor sobra
public class OficinaFormatoInforrectoException extends RuntimeException {
    public OficinaFormatoInforrectoException() {
        super("El formato de la oficina es incorrecto");
    }
}
