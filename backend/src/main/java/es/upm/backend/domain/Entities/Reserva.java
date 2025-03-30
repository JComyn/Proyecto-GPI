package es.upm.backend.domain.Entities;

import java.util.Date;

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
@Table(name = "reservas-extras")
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_cliente"))
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "coche_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_coche"))
    private Coche coche;
    @OneToOne
    @JoinColumn(name = "oficina_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_oficina"))
    private Oficina oficina;
    @OneToOne
    @JoinColumn(name = "oficina_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_oficina"))
    private Oficina oficinaDeevolucion;
    @Column(name = "fechaHoraRecogida", nullable = false)
    private Date fechaHoraRecogida;
    @Column(name = "fechaHoraDevolucion", nullable = false)
    private Date fechaHoraDevolucion;
    @Column(name = "estado", nullable = false)
    private Estado estado;
}