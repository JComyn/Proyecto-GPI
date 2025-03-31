package es.upm.backend.domain.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas-extras")
@Entity
public class ReservaExtras {
    @JoinColumn(name = "reserva_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_reserva"))
    private Reserva Reserva;
    @JoinColumn(name = "extra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_extra"))
    private Extra Extra;
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Column(name = "precioUnit", nullable = false)
    private int precioUnit;
}
