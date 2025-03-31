package es.upm.backend.application.exception;

// Excepcion reutilizable para campos invalidos de JSON, parametros, etc.
public class CampoInvalidoException extends RuntimeException{

    private final String campo;
    private final String detalle;

    public CampoInvalidoException(String campo, String detalle) {
        super("Campo inválido: '" + campo + "'. " + detalle);
        this.campo = campo;
        this.detalle = detalle;
    }

    public String getCampo() {
        return campo;
    }

    public String getDetalle() {
        return detalle;
    }
}
