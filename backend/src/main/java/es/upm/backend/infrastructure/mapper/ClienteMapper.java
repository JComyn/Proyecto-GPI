package es.upm.backend.infrastructure.mapper;

import es.upm.backend.application.dto.CreateClienteDto;
import es.upm.backend.domain.entities.Cliente;

public class ClienteMapper {

    public static Cliente createDto2Entity(CreateClienteDto newCliente){
        return new Cliente(
                null,
                newCliente.nombre(),
                newCliente.apellido(),
                newCliente.email(),
                newCliente.telefono()
        );
    }
}
