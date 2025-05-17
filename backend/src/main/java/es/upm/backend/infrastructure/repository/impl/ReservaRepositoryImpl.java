package es.upm.backend.infrastructure.repository.impl;

import es.upm.backend.application.exception.ReservaNotFoundException;
import es.upm.backend.domain.entities.*;
import es.upm.backend.domain.repository.ReservaRepository;
import es.upm.backend.infrastructure.repository.jpa.ClienteJpaRepository;
import es.upm.backend.infrastructure.repository.jpa.CocheJpaRepository;
import es.upm.backend.infrastructure.repository.jpa.OficinaJpaRepository;
import es.upm.backend.infrastructure.repository.jpa.ReservaJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservaRepositoryImpl implements ReservaRepository {

    private final ReservaJpaRepository reservaJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final CocheJpaRepository cocheJpaRepository;
    private final OficinaJpaRepository oficinaJpaRepository;

    public ReservaRepositoryImpl(ReservaJpaRepository reservaJpaRepository, ClienteJpaRepository clienteJpaRepository,
                                 CocheJpaRepository cocheJpaRepository, OficinaJpaRepository oficinaJpaRepository) {
        this.reservaJpaRepository = reservaJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
        this.cocheJpaRepository = cocheJpaRepository;
        this.oficinaJpaRepository = oficinaJpaRepository;
    }

    @Override
    public List<Reserva> findAll() {
        return reservaJpaRepository.findAll();
    }

    @Override
    public Reserva create(Reserva reserva, Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion) {  //Sustituyo el findById(idCliente).get() por el orElseThrow para evitar el NullPointerException
        Cliente cliente = clienteJpaRepository.findById(idCliente)
                .orElseThrow(() -> new ReservaNotFoundException("Cliente con id " + idCliente + " no encontrado."));
        Coche coche = cocheJpaRepository.findById(idCoche)
                .orElseThrow(() -> new ReservaNotFoundException("Coche con id " + idCoche + " no encontrado."));
        Oficina oficinaRecogida = oficinaJpaRepository.findById(idOficinaRecogida)
                .orElseThrow(() -> new ReservaNotFoundException("Oficina de recogida con id " + idOficinaRecogida + " no encontrada."));
        Oficina oficinaDevolucion = oficinaJpaRepository.findById(idOficinaDevolucion)
                .orElseThrow(() -> new ReservaNotFoundException("Oficina de devolución con id " + idOficinaDevolucion + " no encontrada."));

        reserva.inicializarEntidades(cliente, coche, oficinaRecogida, oficinaDevolucion);
        return reservaJpaRepository.save(reserva);
    }

    @Override
    public void delete(Long idReserva) {
        Optional<Reserva> reserva = reservaJpaRepository.findById(idReserva);
        if (reserva.isEmpty()) {
            throw new ReservaNotFoundException("Reserva con id " + idReserva + " no encontrada.");
        }
        reservaJpaRepository.delete(reserva.get());
    }

    @Override
    public boolean existeReservaSolapada(Long idCoche, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion) {
        return reservaJpaRepository.existeReservaSolapada(idCoche, fechaRecogida, fechaDevolucion);
    }

    @Override
    public boolean cocheEstaraEnOficina(Long idCoche, Long idOficina, LocalDateTime fechaRecogida) {
        Long idOficinaDondeEstara = reservaJpaRepository.findUltimaOficinaAntesDeFecha(idCoche, fechaRecogida);
        if(idOficinaDondeEstara==null){
            return cocheJpaRepository.cocheEstaEnOficina(idCoche, idOficina);
        }
        return Objects.equals(idOficinaDondeEstara, idOficina);
    }

    @Override
    public List<Long> findCochesOcupados(LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion) {
        return reservaJpaRepository.findCochesOcupados(fechaRecogida, fechaDevolucion);
    }

    @Override
    public Reserva realizarReserva(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion,
                                   LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion, TipoTarifa tipoTarifa) {
        Cliente cliente = clienteJpaRepository.findById(idCliente)
                .orElseThrow(() -> new ReservaNotFoundException("Cliente con id " + idCliente + " no encontrado."));
        Coche coche = cocheJpaRepository.findById(idCoche)
                .orElseThrow(() -> new ReservaNotFoundException("Coche con id " + idCoche + " no encontrado."));
        Oficina oficinaRecogida = oficinaJpaRepository.findById(idOficinaRecogida)
                .orElseThrow(() -> new ReservaNotFoundException("Oficina de recogida con id " + idOficinaRecogida + " no encontrada."));
        Oficina oficinaDevolucion = oficinaJpaRepository.findById(idOficinaDevolucion)
                .orElseThrow(() -> new ReservaNotFoundException("Oficina de devolución con id " + idOficinaDevolucion + " no encontrada."));

        Reserva reserva = new Reserva(
                null,
                cliente,
                coche,
                oficinaRecogida,
                oficinaDevolucion,
                fechaHoraRecogida,
                fechaHoraDevolucion,
                tipoTarifa,
                Estado.APROBADO,
                0
        );
        return reservaJpaRepository.save(reserva);
    }

    public Reserva save(Reserva reserva) {
        return reservaJpaRepository.save(reserva);
    }
}
