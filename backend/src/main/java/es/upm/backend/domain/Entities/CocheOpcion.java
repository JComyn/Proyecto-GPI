package es.upm.backend.domain.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coches-opciones")
@Entity
public class CocheOpcion {
    @JoinColumn(name = "coche_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_coche"))
    private Coche coche;
    @JoinColumn(name = "opcion_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_opcion"))
    private Opcion opcion;
}