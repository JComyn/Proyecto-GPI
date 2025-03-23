import React from "react";
import "./style.css";
import PropTypes from "prop-types";

function ConfirmacionReserva({ reservationData, onBack }) {
  const reserva = reservationData || {
    pickupOffice: "Madrid",
    returnOffice: "Barcelona",
    pickupDate: "2025-03-22",
    returnDate: "2025-03-25",
    car: {
      marca: "Toyota",
      modelo: "RAV4 Híbrido",
    },
  };

  return (
    <div className="confirmation-container">
      <h1 className="confirmation-title">¡Reserva Confirmada!</h1>
      <p className="confirmation-message">Tu reserva ha sido confirmada correctamente.</p>
      <div className="confirmation-details">
        <h3>Detalles de la Reserva:</h3>
        <p><strong>Oficina de recogida:</strong> {reserva.pickupOffice}</p>
        <p><strong>Oficina de devolución:</strong> {reserva.returnOffice}</p>
        <p><strong>Fecha de recogida:</strong> {reserva.pickupDate}</p>
        <p><strong>Fecha de devolución:</strong> {reserva.returnDate}</p>
        <p><strong>Coche reservado:</strong> {reserva.car.marca} {reserva.car.modelo}</p>
      </div>
      <button className="confirmation-button" onClick={onBack}>
        Volver al inicio
      </button>
    </div>
  );
}

// Validación de las propiedades con PropTypes
ConfirmacionReserva.propTypes = {
    reservationData: PropTypes.shape({
      pickupOffice: PropTypes.string.isRequired,
      returnOffice: PropTypes.string.isRequired,
      pickupDate: PropTypes.string.isRequired,
      returnDate: PropTypes.string.isRequired,
      car: PropTypes.shape({
        marca: PropTypes.string.isRequired,
        modelo: PropTypes.string.isRequired,
      }).isRequired,
    }),
    onBack: PropTypes.func.isRequired,
  };

export default ConfirmacionReserva;