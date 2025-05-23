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
    @Enumerated(value = EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaCoche categoria;
    @Column(name = "puertas")
    private short puertas;
    @Column(name = "techoSolar", nullable = false)
    private boolean techoSolar;
    @ElementCollection
    private List<String> extras;

    @Column(name = "tarifa_diaria")
    private Double tarifaDiaria;

    @Column(name = "tarifa_semanal")
    private Double tarifaSemanal;

    @Column(name = "tarifa_mensual")
    private Double tarifaMensual;

    /*@Enumerated(value = EnumType.STRING)
    @Embedded
    private TipoTarifa tarifa;*/ // Este bloque comentado parece ser un enfoque anterior y no coincide con las columnas de tarifas numéricas.

    @ManyToOne
    @JoinColumn(name = "oficina_id")
    @JsonBackReference
    private Oficina oficina;
}