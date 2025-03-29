package es.upm.backend.application.exception;

// Excepcion de tarjeta invalida
// Podria sustituirse por CampoInvalidoException
// tomando el campo como "tarjeta" y el detalle como "formato incorrecto"
/*public class TarjetaInvalidaException extends CampoInvalidoException{
    public TarjetaInvalidaException() {
        super("tarjeta", "formato incorrecto");
    }
}*/

public class TarjetaInvalidaException extends RuntimeException{
    public TarjetaInvalidaException() {
        super("El formato de la tarjeta es incorrecto");
    }
}
