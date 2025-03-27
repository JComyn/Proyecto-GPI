package es.upm.backend.application.exception;

public class OficinasEmptyException extends RuntimeException{
    public OficinasEmptyException() {
        super("No se han encontrado oficinas");
    }
}
