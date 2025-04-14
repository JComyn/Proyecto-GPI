package es.upm.backend.infrastructure.repository.jpa;

import es.upm.backend.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    // No es necesario implementar el método save, ya que JpaRepository ya lo proporciona
    boolean existsById(Long idCliente); // Método para verificar si existe un cliente por su ID
}
