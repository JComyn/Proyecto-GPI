package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OficinaJpaRepository extends JpaRepository<Oficina, Long> {

}
