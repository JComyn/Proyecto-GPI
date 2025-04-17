package es.upm.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "categoria", nullable = false)
    private String categoria;
    @Column(name = "puertas")
    private short puertas;
    @Column(name = "techoSolar", nullable = false)
    private boolean techoSolar;
    @ElementCollection
    private List<String> extras;

    @Embedded
    private Tarifa tarifa;

    @ManyToOne
    @JoinColumn(name = "oficina_id")
    @JsonBackReference
    private Oficina oficina;
}