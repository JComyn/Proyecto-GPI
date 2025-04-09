package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
}
