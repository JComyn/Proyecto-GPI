package es.upm.backend.application.services;

import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.exception.ReservasEmptyException;
import es.upm.backend.application.exception.VehiculoNoDisponibleException;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.domain.repository.CocheRepository;
import es.upm.backend.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CocheRepository cocheRepository;

    public ReservaService(ReservaRepository reservaRepository, CocheRepository cocheRepository) {
        this.reservaRepository = reservaRepository;
        this.cocheRepository = cocheRepository;
    }

    public List<Reserva> findAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        if (reservas.isEmpty()) {
            throw new ReservasEmptyException("No hay reservas disponibles.");
        }
        return reservas;
    }

    public Reserva create(Reserva newReserva, Long idCoche, Long idCliente, Long idOficinaRecogida,
            Long idOficinaDevolucion) {
        return reservaRepository.create(newReserva, idCoche, idCliente, idOficinaRecogida, idOficinaDevolucion);
    }

    public void delete(Long idReserva) {
        reservaRepository.delete(idReserva);
    }

    public Reserva realizarReserva(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion,
            LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion) {
        // Validar la disponibilidad del vehículo
        if (reservaRepository.existeReservaSolapada(idCoche, fechaHoraRecogida, fechaHoraDevolucion)) {
            throw new VehiculoNoDisponibleException();
        }

        // Validar la reserva
        if (!reservaValida(idCoche, idOficinaRecogida, fechaHoraRecogida, fechaHoraDevolucion)) {
            throw new ReservaInvalidaException("La reserva no es válida.");
        }

        // Crear la reserva si es válida
        return reservaRepository.realizarReserva(idCoche, idCliente, idOficinaRecogida, idOficinaDevolucion,
                fechaHoraRecogida, fechaHoraDevolucion);
    }

    public boolean reservaValida(Long idCoche, Long idOficina, LocalDateTime fechaRecogida,
            LocalDateTime fechaDevolucion) {
        // Validar que las fechas sean válidas
        if (fechaRecogida.isAfter(fechaDevolucion)) {
            throw new ReservaInvalidaException("La fecha de recogida no puede ser posterior a la fecha de devolución.");
        }
        if (fechaRecogida.isBefore(fechaRecogida)) {
            throw new ReservaInvalidaException("La fecha de recogida no puede estar en el pasado.");
        }

        // Validar que no exista una reserva solapada para el coche
        if (reservaRepository.existeReservaSolapada(idCoche, fechaRecogida, fechaDevolucion)) {
            throw new ReservaInvalidaException("El coche ya tiene una reserva en el rango de fechas especificado.");
        }

        // Validar que el coche estará disponible en la oficina indicada
        if (!reservaRepository.cocheEstaraEnOficina(idCoche, idOficina, fechaRecogida)) {
            throw new ReservaInvalidaException(
                    "El coche no estará disponible en la oficina indicada en la fecha de recogida.");
        }

        return true;
    }

    public List<Coche> getCochesDisponibles(Long idOficinaRecogida, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion){
        List<Coche> todosLosCoches = cocheRepository.findAll();
        if(reservaRepository.findAll().isEmpty()) return todosLosCoches;
        List<Long> idsCochesOcupados = reservaRepository.findCochesOcupados(fechaRecogida, fechaDevolucion);

        List<Coche> cochesDisponibles = todosLosCoches.stream()
                .filter(coche -> !idsCochesOcupados.contains(coche.getId())) // Coche no esta reservado en esas fechas
                .filter(coche -> reservaRepository.cocheEstaraEnOficina(coche.getId(), idOficinaRecogida, fechaRecogida)) // Coche estara en la oficina de recogida
                .toList();

        if(cochesDisponibles.isEmpty()) throw new ReservaInvalidaException("No existen coches disponibles para esos parametros de reserva.");

        return cochesDisponibles;
    }
}
