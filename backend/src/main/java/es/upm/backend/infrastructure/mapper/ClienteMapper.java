package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateClienteNegocioDto;
import es.upm.backend.application.dto.CreateClienteParticularDto;
import es.upm.backend.domain.entities.Cliente;

public class ClienteMapper {

    public static Cliente createDto2Negocio(CreateClienteNegocioDto newCliente){
        return new Cliente(
                null,
                newCliente.nombre(),
                null,
                newCliente.email(),
                newCliente.password(),
                null,
                newCliente.nif(),
                null,
                true
        );
    }

    public static Cliente createDto2Particular(CreateClienteParticularDto newCliente){
        return new Cliente(
                null,
                newCliente.nombre(),
                newCliente.apellidos(),
                newCliente.email(),
                newCliente.password(),
                newCliente.direccion(),
                null,
                newCliente.nacimiento(),
                false
        );
    }
}
