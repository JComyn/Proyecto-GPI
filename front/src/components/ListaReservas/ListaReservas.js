import React from 'react';
import './style.css';

const ListaReservas = () => {
    // Datos ficticios de ejemplo
    const reservas = [
        { id: 1, vehiculo: 'Toyota Corolla', fechaInicio: '2025-05-10', fechaFin: '2025-05-15', estado: 'Confirmada' },
        { id: 2, vehiculo: 'Honda Civic', fechaInicio: '2025-05-20', fechaFin: '2025-05-25', estado: 'Pendiente' },
        { id: 3, vehiculo: 'Ford Focus', fechaInicio: '2025-06-01', fechaFin: '2025-06-05', estado: 'Cancelada' },
    ];

    return (
        <div>
            <h1>Mis Reservas</h1>
            <ul>
                {reservas.map((reserva) => (
                    <li key={reserva.id}>
                        <p><strong>Vehículo:</strong> {reserva.vehiculo}</p>
                        <p><strong>Fechas:</strong> {reserva.fechaInicio} - {reserva.fechaFin}</p>
                        <p><strong>Estado:</strong> {reserva.estado}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ListaReservas;