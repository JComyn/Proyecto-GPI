package es.upm.backend.domain.repository;


import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.domain.entities.TipoTarifa;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository {

    List<Reserva> findAll();
    Reserva create(Reserva reserva, Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion);
    void delete(Long idReserva);
    boolean existeReservaSolapada(Long idCoche, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion);
    boolean cocheEstaraEnOficina(Long idCoche, Long idOficina, LocalDateTime fechaRecogida);
    List<Long> findCochesOcupados(LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion);
    Reserva realizarReserva(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion, TipoTarifa tipoTarifa);
    Reserva save(Reserva reserva);
}
