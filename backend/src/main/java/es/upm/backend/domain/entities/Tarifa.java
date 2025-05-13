package es.upm.backend.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {

    private int diaria;
    private int semanal;
    private int mensual;
}
