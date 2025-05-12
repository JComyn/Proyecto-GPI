package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Coche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CocheJpaRepository extends JpaRepository<Coche, Long> {

    List<Coche> findAllByOficinaId(Long oficinaId);

    @Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
    FROM Coche c
    WHERE c.id = :idCoche AND c.oficina.id = :idOficina
    """)
    boolean cocheEstaEnOficina(
            @Param("idCoche") Long idCoche,
            @Param("idOficina") Long idOficina
    );
}
