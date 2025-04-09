package es.upm.backend.application.services;

import es.upm.backend.domain.entities.Reserva;
import es.upm.backend.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    public Reserva create(Reserva newReserva, Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion){
        return reservaRepository.create(newReserva, idCoche, idCliente, idOficinaRecogida, idOficinaDevolucion);
    }

    public void delete(Long idReserva){
        reservaRepository.delete(idReserva);
    }

    public Reserva realizarReserva(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion){
        //TODO: similar a reservaValida
        return reservaRepository.realizarReserva(idCoche, idCliente, idOficinaRecogida, idOficinaDevolucion, fechaHoraRecogida, fechaHoraDevolucion);
    }

    public boolean reservaValida(Long idCoche, Long idOficina, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion){
        //TODO: validacion de la existencia de las entidades mencionadas, validez de las fechas y manejo de excepciones
        if(reservaRepository.existeReservaSolapada(idCoche, fechaRecogida, fechaDevolucion)){
            return false;
        }
        if(reservaRepository.cocheEstaraEnOficina(idCoche, idOficina, fechaRecogida)){
            return false;
        }
        return true;
    }
}
