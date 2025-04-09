package es.upm.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "oficinas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Oficina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Coche> coches;

    public void agregarCoche(Coche coche) {
        coches.add(coche);
        coche.setOficina(this); // ← importante mantener ambos lados sincronizados
    }
}
