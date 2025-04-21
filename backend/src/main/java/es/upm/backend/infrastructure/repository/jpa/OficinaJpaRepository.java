package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OficinaJpaRepository extends JpaRepository<Oficina, Long> {

    boolean existsByDireccion(String direccion);
    Oficina findByDireccion(String direccion);
    Optional<Oficina> findById(Long id);


}
