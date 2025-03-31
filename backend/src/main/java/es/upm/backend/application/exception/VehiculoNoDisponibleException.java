package es.upm.backend.application.exception;

public class VehiculoNoDisponibleException extends RuntimeException{
    public VehiculoNoDisponibleException() {
        super("El vehículo no está disponible en la fecha seleccionada");
    }
}
