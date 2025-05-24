import React from "react";
import "./style.css";
import PropTypes from "prop-types";
import { mockOffices } from "../SearchForm/mockData";
import { useOficinas } from "hooks/useOficinas";

function ConfirmacionReserva({ reservationData, onBack }) {

  const {oficinas, errorOficinas} = useOficinas();
  if(errorOficinas) alert("No hay oficinas en la BBDD");


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
  const pickupOfficeName = oficinas.find((office) => office.id === pickupOfficeId)?.direccion || "Oficina desconocida";
  const returnOfficeName = oficinas.find((office) => office.id === returnOfficeId)?.direccion || "Oficina desconocida";

  // Calcular días de diferencia entre fechas
  function calcularDias(fechaInicio, fechaFin) {
    const inicio = new Date(fechaInicio);
    const fin = new Date(fechaFin);
    const diffTime = Math.abs(fin - inicio);
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  }

  // Calcular precio según tarifas
  function calcularPrecio(dias, tarifas) {
    if (!tarifas) return 0;
    // Si hay tarifa mensual y la reserva es de 30 días o más
    if (tarifas.tarifaMensual && dias >= 30) {
      const meses = Math.floor(dias / 30);
      const diasRestantes = dias % 30;
      return meses * tarifas.tarifaMensual + calcularPrecio(diasRestantes, tarifas);
    }
    // Si hay tarifa semanal y la reserva es de 7 días o más
    if (tarifas.tarifaSemanal && dias >= 7) {
      const semanas = Math.floor(dias / 7);
      const diasRestantes = dias % 7;
      return semanas * tarifas.tarifaSemanal + calcularPrecio(diasRestantes, tarifas);
    }
    // Si hay tarifa diaria
    if (tarifas.tarifaDiaria) {
      return dias * tarifas.tarifaDiaria;
    }
    return 0;
  }

  const diasReserva = calcularDias(pickupDate, returnDate);
  const precioEstimado = calcularPrecio(diasReserva, {
    tarifaDiaria: car.tarifaDiaria,
    tarifaSemanal: car.tarifaSemanal,
    tarifaMensual: car.tarifaMensual,
  });

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
        <p><strong>Precio:</strong> {precioEstimado} €</p>
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