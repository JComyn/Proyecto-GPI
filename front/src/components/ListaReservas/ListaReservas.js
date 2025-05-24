import React, { useEffect, useState } from "react";
import './style.css';
import { getUserId } from "services/contextService";
import { obtenerReservas } from "services/reservaService";

const ListaReservas = () => {
    const [reservas, setReservas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchReservas = async () => {
            try {
                const todas = await obtenerReservas();
                const userId = parseInt(getUserId());
                setReservas(todas.filter(r => r.cliente && r.cliente.id === userId));
            } catch (e) {
                setReservas([]);
            } finally {
                setLoading(false);
            }
        };
        fetchReservas();
    }, []);

    if (loading) return <div>Cargando reservas...</div>;

    return (
        <div className="reservas-container">
            <h1 className="reservas-title">Mis Reservas</h1>
            {reservas.length === 0 ? (
                <p className="no-reservas">No tienes reservas.</p>
            ) : (
                <ul className="reservas-list">
                    {reservas.map((reserva) => (
                        <li className="reserva-card" key={reserva.id}>
                            <p><strong>Vehículo:</strong> {reserva.coche.marca} {reserva.coche.modelo}</p>
                            <p><strong>Extras:</strong> {reserva.coche.extras && reserva.coche.extras.length > 0 ? reserva.coche.extras.join(", ") : "Ninguno"}</p>
                            <p><strong>Fechas:</strong> {reserva.fechaHoraRecogida.split("T")[0]} - {reserva.fechaHoraDevolucion.split("T")[0]}</p>
                            <p><strong>Oficina recogida:</strong> {reserva.oficinaRecogida?.direccion}</p>
                            <p><strong>Oficina devolución:</strong> {reserva.oficinaDevolucion?.direccion}</p>
                            <p><strong>Tipo de tarifa:</strong> {reserva.tipoTarifa}</p>
                            <p><strong>Precio total:</strong> {reserva.precio} €</p>
                            <p><strong>Estado:</strong> {reserva.estado}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ListaReservas;