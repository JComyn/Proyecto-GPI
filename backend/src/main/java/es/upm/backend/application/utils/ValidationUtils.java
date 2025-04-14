package es.upm.backend.application.utils;

import java.util.List;

import es.upm.backend.domain.entities.Oficina;

public class ValidationUtils {

    public static boolean isFormatoValido(Oficina newOficina) {
        // Verificar que la oficina no sea nula
        if (newOficina == null || newOficina.getDireccion() == null || newOficina.getDireccion().isBlank()) {
            return false;
        }

        // Obtener la dirección de la oficina
        String direccion = newOficina.getDireccion();

        // Lista de palabras válidas para el inicio de la dirección
        List<String> palabrasValidas = List.of("Calle", "Avenida", "Paseo", "Plaza", "Camino");

        // Dividir la dirección en palabras
        String[] partes = direccion.split("\\s+");

        // Verificar que la dirección tenga al menos 3 partes: tipo, nombre y número
        if (partes.length < 3 || !palabrasValidas.contains(partes[0])) {
            return false;
        }

        // Verificar que la última parte sea un número
        String ultimaParte = partes[partes.length - 1];
        return ultimaParte.matches("\\d+");
    }
}