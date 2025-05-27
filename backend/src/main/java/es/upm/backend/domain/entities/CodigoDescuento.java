package es.upm.backend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codigosDescuento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CodigoDescuento {

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(name = "descuento", nullable = false)
    private float descuento;

}
