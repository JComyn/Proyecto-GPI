

import React, { useState } from 'react';
import SearchForm from '../components/SearchForm';
import TarjetaCocheUI from "../components/TarjetaCoche";
import FormularioPago from "../components/FormularioPago"; // Asegúrate de que el nombre coincida con el archivo exportado
import mockCar from '../components/TarjetaCoche/mockCar';
import ConfirmacionReserva from '../components/ConfReserva/ConfirmacionReserva';
import mockSearchForm from '../components/SearchForm/mockSearchForm';

export default function Reservar() {

  const [showCar, setShowCar] = useState(false); // Estado para controlar la visibilidad de la tarjeta
  const [showPayment, setShowPayment] = useState(false); // Estado para controlar la visibilidad del formulario de pago
  const [showConfirmation, setShowConfirmation] = useState(false); // Controla la visibilidad de la confirmación de reserva
  const [searchData, setSearchData] = useState(null); // Datos del formulario de búsqueda
  const [selectedCar, setSelectedCar] = useState(null); // Coche seleccionado
  const [reservationData, setReservationData] = useState(null); // Datos de la reserva

  const handleSearch = (searchData) => {
    console.log("Search form data:", searchData);
    setShowCar(true); // Mostrar la tarjeta del coche
    setShowPayment(false); // Ocultar el formulario de pago si estaba visible
    setShowConfirmation(false); // Ocultar la confirmación de reserva
    setSearchData(searchData); // Guardar los datos del formulario
  };

  const handleSelectCar = () => {
    setSelectedCar(mockCar); // Seleccionar el coche
    setShowPayment(true); // Mostrar el formulario de pago
    window.scrollTo({ top: 0, behavior: 'smooth' }); // Scroll al inicio para ver el formulario
  };

  const handleConfirmReservation = (data) => {
    setReservationData(data); // Guardar los datos de la reserva
    setShowConfirmation(true); // Mostrar la pantalla de confirmación
  };

  const handleConfirmPayment = () => {
    // Combinar los datos del formulario de búsqueda y del coche seleccionado
    const reservation = {
      ...searchData,
      car: selectedCar,
    };
    setReservationData(reservation); // Guardar los datos de la reserva
    setShowConfirmation(true); // Mostrar la pantalla de confirmación
    setShowPayment(false); // Ocultar el formulario de pago
  };

  const handleBackToHome = () => {
    setShowCar(false); // Volver al formulario de búsqueda
    setShowPayment(false); // Ocultar el formulario de pago
    setSearchData(false); // Limpiar los datos de búsqueda
    setSelectedCar(false); // Limpiar el coche seleccionado
    setShowConfirmation(false); // Volver al formulario de búsqueda
    setReservationData(null); // Limpiar los datos de la reserva
  };

  return (
    <div className="App">
      <main>

        {/* Formulario de búsqueda */}
        {!showCar && !showPayment && !showConfirmation && (
          <SearchForm onSearch={handleSearch} />
        )}

        {/* Tarjeta del coche */}
        {showCar && !showPayment && !showConfirmation && (
          <TarjetaCocheUI coche={mockCar} onSelectCar={handleSelectCar} />
        )}

        {/* Formulario de pago */}
        {showPayment && !showConfirmation && (
          <FormularioPago onConfirmPayment={handleConfirmPayment} />
        )}

        {/* Confirmación de reserva */}
        {showConfirmation && (
          <ConfirmacionReserva
            reservationData={reservationData}
            onBack={handleBackToHome}
          />
        )}

      </main>
    </div>
  );
}