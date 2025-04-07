import React from "react";
import PropTypes from "prop-types";
import "./style.css";

function TarjetaCocheUI({ coche, onSelectCar, mostrarBoton = true }) {
  const handleSelectCar = () => {
    // Mostramos una alerta
    alert("Coche seleccionado, redirigiendo a la página de pago...");
    // Llamamos a la función proporcionada por el componente padre
    onSelectCar();
  };

  return (
    <div className="car-card-container">
      <h2 className="car-card-title">{coche.marca} {coche.modelo}</h2>
      <div className="car-card-details">
        <div className="car-card-section">
          <p><strong>Categoría:</strong> {coche.categoria}</p>
          <p><strong>Puertas:</strong> {coche.puertas}</p>
          <p><strong>Transmisión:</strong> {coche.transmision}</p>
          <p><strong>Techo solar:</strong> {coche.techoSolar ? "Sí" : "No"}</p>
        </div>
        <div className="car-card-section">
          <h3>Tarifas</h3>
          <p><strong>Diaria:</strong> {coche.tarifa.diaria} €/día</p>
          <p><strong>Semanal:</strong> {coche.tarifa.semanal} €/semana</p>
          <p><strong>Mensual:</strong> {coche.tarifa.mensual} €/mes</p>
        </div>
        <div className="car-card-section">
          <h3>Extras</h3>
          <ul>
            {coche.extras.map((extra, index) => (
              <li key={index}>{extra}</li>
            ))}
          </ul>
        </div>
      </div>
      <div className="car-card-actions">
        {mostrarBoton && (
          <button className="select-car-button" onClick={handleSelectCar}>
            RESERVAR
          </button>
        )}
      </div>
    </div>
  );
}

TarjetaCocheUI.propTypes = {
  coche: PropTypes.shape({
    marca: PropTypes.string.isRequired,
    modelo: PropTypes.string.isRequired,
    categoria: PropTypes.string.isRequired,
    puertas: PropTypes.number.isRequired,
    transmision: PropTypes.string.isRequired,
    techoSolar: PropTypes.bool.isRequired,
    tarifa: PropTypes.shape({
      diaria: PropTypes.number.isRequired,
      semanal: PropTypes.number.isRequired,
      mensual: PropTypes.number.isRequired,
    }).isRequired,
    extras: PropTypes.arrayOf(PropTypes.string).isRequired,
  }).isRequired,
  onSelectCar: PropTypes.func.isRequired,
  mostrarBoton: PropTypes.bool,
};

export default TarjetaCocheUI;