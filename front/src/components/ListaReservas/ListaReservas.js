import React, { useState } from "react";
import './style.css';
import { useNavigate } from "react-router-dom";
import { useAuth } from "hooks/useAuth";
import { getUserId } from "services/contextService";

const ListaReservas = () => {
    // Datos ficticios de ejemplo
    const reservas = [
        { id: 1, vehiculo: 'Toyota Corolla', fechaInicio: '2025-05-10', fechaFin: '2025-05-15', estado: 'Confirmada' },
        { id: 2, vehiculo: 'Honda Civic', fechaInicio: '2025-05-20', fechaFin: '2025-05-25', estado: 'Pendiente' },
        { id: 3, vehiculo: 'Ford Focus', fechaInicio: '2025-06-01', fechaFin: '2025-06-05', estado: 'Cancelada' },
    ];


    return (
        <div className="reservas-container">
        <h1 className="reservas-title">Mis Reservas</h1>
        {reservas.length === 0 ? (
            <p className="no-reservas">No tienes reservas.</p>
        ) : (
            <ul className="reservas-list">
                {reservas.map((reserva) => (
                    <li className="reserva-card" key={reserva.id}>
                        <p><strong>Vehículo:</strong> {reserva.vehiculo}</p>
                        <p><strong>Fechas:</strong> {reserva.fechaInicio} - {reserva.fechaFin}</p>
                        <p><strong>Estado:</strong> {reserva.estado}</p>
                    </li>
                ))}
            </ul>
        )}
    </div>
    );
};

export default ListaReservas;