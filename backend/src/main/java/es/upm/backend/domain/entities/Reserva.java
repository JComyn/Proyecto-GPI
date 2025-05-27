package es.upm.backend.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas")
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_cliente"))
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "coche_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_coche"))
    private Coche coche;
    @OneToOne
    @JoinColumn(name = "oficina_recogida_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_oficina"))
    private Oficina oficinaRecogida;
    @OneToOne
    @JoinColumn(name = "oficina_devolucion_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_oficina"))
    private Oficina oficinaDevolucion;
    @Column(name = "fechaHoraRecogida", nullable = false)
    private LocalDateTime fechaHoraRecogida;
    @Column(name = "fechaHoraDevolucion", nullable = false)
    private LocalDateTime fechaHoraDevolucion;
    @Enumerated(value = EnumType.STRING)
    private TipoTarifa tipoTarifa;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;
    @Column(name = "precio", nullable = false)
    private double precio;


    public void inicializarEntidades(Cliente cliente, Coche coche, Oficina oficinaRecogida, Oficina oficinaDevolucion){
        this.cliente = cliente;
        this.coche = coche;
        this.oficinaRecogida = oficinaRecogida;
        this.oficinaDevolucion = oficinaDevolucion;
    }
}