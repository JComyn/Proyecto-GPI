package es.upm.backend.application.services;

import es.upm.backend.application.exception.ReservaInvalidaException;
import es.upm.backend.application.exception.ReservasEmptyException;
import es.upm.backend.application.exception.VehiculoNoDisponibleException;
import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
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
}
