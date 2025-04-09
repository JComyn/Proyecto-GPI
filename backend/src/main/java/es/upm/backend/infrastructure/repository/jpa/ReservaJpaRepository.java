package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaJpaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reserva r
        WHERE r.coche.id = :idCoche
        AND r.fechaHoraRecogida < :fechaFin
        AND r.fechaHoraDevolucion > :fechaInicio
    """)
    boolean existeReservaSolapada(
            @Param("idCoche") Long idCoche,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("""
        SELECT r.oficinaDevolucion.id
        FROM Reserva r
        WHERE r.coche.id = :idCoche AND r.fechaHoraDevolucion <= :fechaReferencia
        ORDER BY r.fechaHoraDevolucion DESC
        LIMIT 1
    """)
    Long findUltimaOficinaAntesDeFecha(
            @Param("idCoche") Long idCoche,
            @Param("fechaReferencia") LocalDateTime fechaReferencia
    );
}
