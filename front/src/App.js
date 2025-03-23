import React, { useState } from "react";
import SearchForm from "./components/SearchForm";
import ConfirmacionReserva from "./components/ConfReserva/ConfirmacionReserva";

function App() {
  const [showConfirmation, setShowConfirmation] = useState(false); // Estado para controlar qué pantalla mostrar
  const [reservationData, setReservationData] = useState(null); // Datos de la reserva

  const handleSearch = (searchData) => {
    console.log("Search form data:", searchData);
    alert("Search successful! Redirecting to available cars would happen here.");
  };

  const handleConfirmReservation = (data) => {
    setReservationData(data); // Guardar los datos de la reserva
    setShowConfirmation(true); // Mostrar la pantalla de confirmación
  };

  const handleBackToHome = () => {
    setShowConfirmation(false); // Volver al formulario de búsqueda
    setReservationData(null); // Limpiar los datos de la reserva
  };

  return (
    <div className="App">
      <main>
        {!showConfirmation ? (
          <SearchForm onSearch={handleSearch} onConfirm={handleConfirmReservation} />
        ) : (
          <ConfirmacionReserva reservationData={reservationData} onBack={handleBackToHome} />
        )}
      </main>
    </div>
  );
}

export default App;