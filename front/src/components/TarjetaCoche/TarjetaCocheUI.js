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

  // Comprobación para asegurar que coche y sus propiedades de tarifa existen
  if (!coche || typeof coche.tarifaDiaria === 'undefined' || typeof coche.tarifaSemanal === 'undefined' || typeof coche.tarifaMensual === 'undefined') {
    // Renderizamos un mensaje de carga si los datos no están disponibles
    return <div className="loading-placeholder">Cargando datos del coche...</div>;
  }

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
          {/* Accedemos directamente a las propiedades de tarifa del objeto coche */}
          <p><strong>Diaria:</strong> {coche.tarifaDiaria !== undefined ? `${coche.tarifaDiaria} €/día` : 'N/A'}</p>
          <p><strong>Semanal:</strong> {coche.tarifaSemanal !== undefined ? `${coche.tarifaSemanal} €/semana` : 'N/A'}</p>
          <p><strong>Mensual:</strong> {coche.tarifaMensual !== undefined ? `${coche.tarifaMensual} €/mes` : 'N/A'}</p>
        </div>
        <div className="car-card-section">
          <h3>Extras</h3>
          <ul>
            {/* Asegurarse de que coche.extras es un array antes de mapear */}
            {Array.isArray(coche.extras) && coche.extras.map((extra, index) => (
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
    // Actualizamos las propTypes para las tarifas planas
    tarifaDiaria: PropTypes.number, // Puede ser undefined si aún no se cargó
    tarifaSemanal: PropTypes.number, // Puede ser undefined si aún no se cargó
    tarifaMensual: PropTypes.number, // Puede ser undefined si aún no se cargó
    extras: PropTypes.arrayOf(PropTypes.string).isRequired,
  }).isRequired,
  onSelectCar: PropTypes.func.isRequired,
  mostrarBoton: PropTypes.bool,
};

export default TarjetaCocheUI;