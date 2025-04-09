package es.upm.backend.infrastructure.repository.impl;

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

    public ReservaRepositoryImpl(ReservaJpaRepository reservaJpaRepository, ClienteJpaRepository clienteJpaRepository, CocheJpaRepository cocheJpaRepository, OficinaJpaRepository oficinaJpaRepository){
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
    public Reserva create(Reserva reserva, Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion) {
        Cliente cliente = clienteJpaRepository.findById(idCliente).get();
        Coche coche = cocheJpaRepository.findById(idCoche).get();
        Oficina oficinaRecogida = oficinaJpaRepository.findById(idOficinaRecogida).get();
        Oficina oficinaDevolucion = oficinaJpaRepository.findById(idOficinaDevolucion).get();
        reserva.inicializarEntidades(cliente, coche, oficinaRecogida, oficinaDevolucion);
        return reservaJpaRepository.save(reserva);
    }

    @Override
    public void delete(Long idReserva) {
        Optional<Reserva> reserva = reservaJpaRepository.findById(idReserva);
        reservaJpaRepository.delete(reserva.get());
    }

    @Override
    public boolean existeReservaSolapada(Long idCoche, LocalDateTime fechaRecogida, LocalDateTime fechaDevolucion) {
        return reservaJpaRepository.existeReservaSolapada(idCoche, fechaRecogida, fechaDevolucion);
    }

    @Override
    public boolean cocheEstaraEnOficina(Long idCoche, Long idOficina, LocalDateTime fechaRecogida) {
        Long idOficinaDondeEstara = reservaJpaRepository.findUltimaOficinaAntesDeFecha(idCoche, fechaRecogida);
        return Objects.equals(idOficinaDondeEstara, idOficina);
    }

    @Override
    public Reserva realizarReserva(Long idCoche, Long idCliente, Long idOficinaRecogida, Long idOficinaDevolucion, LocalDateTime fechaHoraRecogida, LocalDateTime fechaHoraDevolucion) {
        Reserva reserva = new Reserva(
                null,
                clienteJpaRepository.findById(idCliente).get(),
                cocheJpaRepository.findById(idCoche).get(),
                oficinaJpaRepository.findById(idOficinaRecogida).get(),
                oficinaJpaRepository.findById(idOficinaDevolucion).get(),
                fechaHoraRecogida,
                fechaHoraDevolucion,
                Estado.APROBADO
        );
        return reservaJpaRepository.save(reserva);
    }
}
