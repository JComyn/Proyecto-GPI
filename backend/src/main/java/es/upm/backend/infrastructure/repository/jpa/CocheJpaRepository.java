package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocheJpaRepository extends JpaRepository<Coche, Long> {

    List<Coche> findAllByOficinaId(Long oficinaId);
}
