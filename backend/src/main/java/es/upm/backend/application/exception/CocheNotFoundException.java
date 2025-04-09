package es.upm.backend.application.exception;

public class CocheNotFoundException extends RuntimeException {
    public CocheNotFoundException(String message) {
        super(message);
    }
}
