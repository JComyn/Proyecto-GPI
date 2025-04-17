import React, { useState } from "react";
import "./VisualizarFlota.css"; // Estilos para el componente
import TarjetaCocheUI from "../TarjetaCoche/TarjetaCocheUI"; // Reutilizamos la tarjeta de coche
import { useFlota } from "hooks/useFlota";

function VisualizarFlota() {
  const [filtro, setFiltro] = useState("");
  const {flota, error} = useFlota();

  if(error) return (<h1>ERROR: {error}</h1>)

  const handleFiltroChange = (e) => {
    setFiltro(e.target.value);
  };

  const cochesFiltrados = (flota).filter((coche) =>
    coche.marca.toLowerCase().includes(filtro.toLowerCase())
  );

  return (
    <div className="flota-container">
      <h1 className="flota-title">Nuestra Flota</h1>
      <div className="filtro-container">
        <input
          type="text"
          placeholder="Filtrar por marca..."
          value={filtro}
          onChange={handleFiltroChange}
          className="filtro-input"
        />
      </div>
      <div className="flota-grid">
        {cochesFiltrados.map((coche, index) => (
          <TarjetaCocheUI key={index} coche={coche} mostrarBoton={false} />
        ))}
      </div>
    </div>
  );
}

export default VisualizarFlota;