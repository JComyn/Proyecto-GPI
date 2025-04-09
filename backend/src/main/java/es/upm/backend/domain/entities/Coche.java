package es.upm.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coches")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "marca", nullable = false)
    private String marca;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "transmision", nullable = false)
    private Transmision transmision;
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "puertas")
    private short puertas;
    @Column(name = "techoSolar", nullable = false)
    private boolean techoSolar;

    @ManyToOne
    @JoinColumn(name = "oficina_id")
    @JsonBackReference
    private Oficina oficina;
}