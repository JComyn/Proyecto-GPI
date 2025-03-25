package es.upm.backend.domain.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private Disponibilidad disponibilidad;
    private Transmision transmision;
    /* Candidatos a ser posible value object */
    private String modelo;
    private String gama;
    private String Marca;
}