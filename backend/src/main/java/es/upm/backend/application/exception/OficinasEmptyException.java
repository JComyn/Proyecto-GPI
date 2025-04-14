package es.upm.backend.application.exception;

public class OficinasEmptyException extends RuntimeException{
    public OficinasEmptyException(String message) {
        super("No se han encontrado oficinas");
    }
}
