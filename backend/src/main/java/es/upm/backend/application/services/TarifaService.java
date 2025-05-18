package es.upm.backend.application.services;
import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.domain.entities.*;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
//import es.upm.backend.domain.repository.ClienteRepository;
//import es.upm.backend.domain.repository.CocheRepository;
//import es.upm.backend.domain.repository.ReservaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TarifaService {

    private final CodigoDescuentoService codigoDescuentoService;
    public TarifaService(CodigoDescuentoService codigoDescuentoService) {
        this.codigoDescuentoService = codigoDescuentoService;
    }

    // La tarifa (tipo de tarifa) puede ser por día más kilometraje, por día con kilómetros ilimitados, semanal, de fin de semana o de larga duración (por meses).
    // Para todas las tarifas, excepto la de larga duración, el precio varía según el periodo del año (enero-mayo, junio-septiembre, octubre-diciembre).
    // La tarifa para un coche depende de la categoría de precio (gama alta, media o baja), el tipo de tarifa y el tipo de cliente (cliente particular o de negocio).
    // En este último caso se aplicará un descuento especial del 30% sobre la tarifa normal, excepto en la tarifa de fin de semana

    public Pair<Double, Boolean> calcularPrecio(Reserva reserva, String codigoDescuento){

        //Categorias de precios segun epoca del año
        Map<Integer, Integer> precioPorTemporada = new HashMap<>();
        precioPorTemporada.put(1, 40); // Enero-mayo -> 40 euros/dia
        precioPorTemporada.put(2, 60); // Junio-septiembre -> 60 euros/dia
        precioPorTemporada.put(3, 30); // Octubre-diciembre -> 30 euros/dia


        // Obtener datos de la reserva
        LocalDateTime fechaHoraRecogida = reserva.getFechaHoraRecogida();
        LocalDateTime fechaHoraDevolucion = reserva.getFechaHoraDevolucion();
        Cliente cliente = reserva.getCliente();
        boolean esNegocio = cliente.isEsNegocio();
        TipoTarifa tipoTarifa = reserva.getTipoTarifa();
        Coche coche = reserva.getCoche();
        CategoriaCoche categoria = coche.getCategoria();


        // Variables para días de fin de semana y entre semana
        int diasFinDeSemana = 0;
        int diasEntreSemana = 0;

        // Mapa para los días por temporada
        Map<Integer, Integer> diasPorTemporada = new HashMap<>();
        diasPorTemporada.put(1, 0); // Enero-mayo
        diasPorTemporada.put(2, 0); // Junio-septiembre
        diasPorTemporada.put(3, 0); // Octubre-diciembre

        LocalDate fechaRecogida = fechaHoraRecogida.toLocalDate();
        LocalDate fechaDevolucion = fechaHoraDevolucion.toLocalDate();

        int temporada, mes;
        // Dividir los días de la reserva en días de fin de semana y entre semana
        // Y calcular los días por temporada
        for (LocalDate fecha = fechaRecogida; !fecha.isAfter(fechaDevolucion); fecha = fecha.plusDays(1)) {
            DayOfWeek dia = fecha.getDayOfWeek();
            if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
                diasFinDeSemana++;
            } else {
                diasEntreSemana++;
            }

            mes = fecha.getMonthValue();
            if (mes <= 5) {
                temporada = 1; // Enero-mayo
            } else if (mes <= 9) {
                temporada = 2; // Junio-septiembre
            } else {
                temporada = 3; // Octubre-diciembre
            }
            diasPorTemporada.put(temporada, diasPorTemporada.get(temporada) + 1);
        }


        // Aplicar precio base según la categoría de precio y la temporada
        double precioCategoria = categoria.getPrecio();
        double precioBase = 0;
        // Aplicar precio por los días de cada temporada
        if (diasPorTemporada.get(1) > 0) {
            precioBase += diasPorTemporada.get(1)*(precioPorTemporada.get(1) + precioCategoria);
        }
        if (diasPorTemporada.get(2) > 0) {
            precioBase += diasPorTemporada.get(2)*(precioPorTemporada.get(2) + precioCategoria);
        }
        if (diasPorTemporada.get(3) > 0) {
            precioBase += diasPorTemporada.get(3)*(precioPorTemporada.get(3) + precioCategoria);
        }

        // TODO: Definir el precio (o peso) de cada tipo de tarifa -> EN GRUPO
        int diasTotal = diasFinDeSemana + diasEntreSemana;
        switch (tipoTarifa) {
            case DIARIA_KILOMETRAJE:
                if (diasFinDeSemana > 0 && diasFinDeSemana <= 2 && diasEntreSemana <= 1) {
                    throw new ReservaInvalidaException("La reserva debe ser de fin de semana para las fechas seleccionadas");
                } else {
                    precioBase *= tipoTarifa.getPrecio();
                    // Mas el precio por kilometraje
                }
                break;
            case DIARIA_ILIMITADO:
                if (diasFinDeSemana > 0 && diasFinDeSemana <= 2 && diasEntreSemana <= 1) {
                    throw new ReservaInvalidaException("La reserva debe ser de fin de semana para las fechas seleccionadas");
                } else {
                    precioBase *= tipoTarifa.getPrecio();
                }
                break;
            case SEMANAL:
                if(diasTotal >= 7) {
                    precioBase *= tipoTarifa.getPrecio();
                } else{
                    throw new ReservaInvalidaException("Para la tarifa semanal, la reserva debe ser de al menos 7 días");
                }
                break;
            case FIN_DE_SEMANA:
                if (diasFinDeSemana > 0 && diasFinDeSemana <= 2 && diasEntreSemana <= 1) {
                    precioBase *= tipoTarifa.getPrecio();
                } else {
                    throw new ReservaInvalidaException("La reserva no es de fin de semana");
                }
                break;
            case LARGA_DURACION: // No se aplica los dias de temporada
                precioBase = precioCategoria;
                if (diasTotal >= 30) {
                    precioBase *= diasTotal * tipoTarifa.getPrecio();
                } else {
                    throw new ReservaInvalidaException("La reserva no es de larga duración");
                }
                break;
            default:
                throw new ReservaInvalidaException("Tipo de tarifa no válido");
        }

        // Aplicar descuento para clientes de negocio
        if (esNegocio && !tipoTarifa.equals(TipoTarifa.FIN_DE_SEMANA)) {
            precioBase *= 0.7;
        }

        // Aplicar descuento por código de descuento

        //Comprobar si el codigo de reserva es valido
        boolean aplicado = false;
        if (codigoDescuento != null && !codigoDescuento.isEmpty()) {
            CodigoDescuento codigo = codigoDescuentoService.findByCodigo(codigoDescuento);
            if (codigo != null) {
                float descuento = codigo.getDescuento(); // Por ejemplo 0.1 -> 10%
                precioBase *= (1 - descuento);
                aplicado = true;
            }
        }

        return new Pair<>(precioBase, aplicado);
    }
}


