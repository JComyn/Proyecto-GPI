package es.upm.backend.domain.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;
    @Column(name = "puertas")
    private short puertas;
    
    @Column(name = "techoSolar", nullable = false)
    private boolean techoSolar;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "disponibilidad", nullable = false)
    private Disponibilidad disponibilidad;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transmision", nullable = false)
    private Transmision transmision;

    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "gama", nullable = false)
    private String gama;
    @Column(name = "marca", nullable = false)
    private String Marca;
}