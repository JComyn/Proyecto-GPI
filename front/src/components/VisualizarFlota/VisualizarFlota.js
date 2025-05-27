import React, { useState } from "react";
import "./VisualizarFlota.css"; // Estilos para el componente
import TarjetaCocheUI from "../TarjetaCoche/TarjetaCocheUI"; // Reutilizamos la tarjeta de coche
import { useFlota } from "hooks/useFlota";

function VisualizarFlota() {
  // Añade más campos de filtro al estado
  const [filtro, setFiltro] = useState("");
  const [categoria, setCategoria] = useState("");
  const [transmision, setTransmision] = useState("");
  const [puertas, setPuertas] = useState("");
  const { flota, error } = useFlota();

  if (error) alert("No hay coches en la BBDD");

  const cochesFiltrados = flota.filter(
    (coche) =>
      (coche.marca.toLowerCase().includes(filtro.toLowerCase()) ||
        coche.modelo.toLowerCase().includes(filtro.toLowerCase())) &&
      (categoria === "" || coche.categoria === categoria) &&
      (transmision === "" || coche.transmision === transmision) &&
      (puertas === "" || coche.puertas === parseInt(puertas))
  );

  return (
    <div className="flota-container">
      <h1 className="flota-title">Nuestra Flota</h1>
      <div className="filtro-container">
        <input
          type="text"
          placeholder="Filtrar por marca o modelo..."
          value={filtro}
          onChange={(e) => setFiltro(e.target.value)}
          className="filtro-input"
        />
        <select
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
        >
          <option value="">Todas las categorías</option>
          <option value="ALTA">Alta</option>
          <option value="MEDIA">Media</option>
          <option value="BAJA">Baja</option>
        </select>
        <select
          value={transmision}
          onChange={(e) => setTransmision(e.target.value)}
        >
          <option value="">Todas</option>
          <option value="AUTOMATICA">Automática</option>
          <option value="MANUAL">Manual</option>
        </select>
        <select value={puertas} onChange={(e) => setPuertas(e.target.value)}>
          <option value="">Cualquier número de puertas</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
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