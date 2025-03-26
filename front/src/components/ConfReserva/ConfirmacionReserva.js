import React from "react";
import "./style.css";
import PropTypes from "prop-types";
import { mockOffices } from "../SearchForm/mockData";

function ConfirmacionReserva({ reservationData, onBack }) {
  if (!reservationData) {
    return <p>No hay datos de reserva disponibles.</p>;
  }

  const {
    pickupOffice,
    returnOffice,
    pickupDate,
    returnDate,
    pickupTime,
    returnTime,
    car,
  } = reservationData;

  // Convertir los IDs a números para garantizar la comparación correcta
  const pickupOfficeId = Number(pickupOffice);
  const returnOfficeId = Number(returnOffice);

  // Buscar los nombres de las oficinas
  const pickupOfficeName = mockOffices.find((office) => office.id === pickupOfficeId)?.name || "Oficina desconocida";
  const returnOfficeName = mockOffices.find((office) => office.id === returnOfficeId)?.name || "Oficina desconocida";

  return (
    <div className="confirmation-container">
      <h2 className="confirmation-title">Confirmación de Reserva</h2>
      <div className="confirmation-details">
        <div className="confirmation-card-section">
          <h3>Detalles de la Reserva</h3>
          <p><strong>Oficina de Recogida:</strong> {pickupOfficeName}</p>
          <p><strong>Fecha y Hora de Recogida:</strong> {pickupDate} a las {pickupTime}</p>
          <p><strong>Oficina de Devolución:</strong> {returnOfficeName}</p>
          <p><strong>Fecha y Hora de Devolución:</strong> {returnDate} a las {returnTime}</p>
        </div>
        <div className="confirmation-card-section">
          <h3>Detalles del Coche</h3>
          <p><strong>Marca:</strong> {car.marca}</p>
          <p><strong>Modelo:</strong> {car.modelo}</p>
          <p><strong>Categoría:</strong> {car.categoria}</p>
          <p><strong>Puertas:</strong> {car.puertas}</p>
          <p><strong>Transmisión:</strong> {car.transmision}</p>
          <p><strong>Extras:</strong> {car.extras.join(", ")}</p>
        </div>
        </div>
        <div className="confirmation-button-container">
        <button className="confirmation-button" onClick={onBack}>
            VOLVER AL INICIO
          </button>
        </div>
          
    </div>
    
  );
}

// Validación de las propiedades con PropTypes
ConfirmacionReserva.propTypes = {
  reservationData: PropTypes.object.isRequired,
  onBack: PropTypes.func.isRequired,
};

export default ConfirmacionReserva;