import React, { useState, useEffect } from 'react';
import SearchForm from '../components/SearchForm';
import TarjetaCocheUI from "../components/TarjetaCoche";
import FormularioPago from "../components/FormularioPago";
import ConfirmacionReserva from '../components/ConfReserva/ConfirmacionReserva';
import { useReserva } from 'hooks/useReserva';
import { getUserId } from 'services/contextService';
import { useNavigate } from 'react-router-dom';

export default function Reservar() {
  const navigate = useNavigate();
  const isAuthenticated = !!getUserId();

  // Verificar autenticación al cargar la página
  useEffect(() => {
    if (!isAuthenticated) {
      alert("Debes iniciar sesión para realizar una reserva");
      navigate("/login");
      return;
    }
  }, [isAuthenticated, navigate]);

  // Si no está autenticado, no renderizar nada
  if (!isAuthenticated) {
    return (
      <div style={{ textAlign: 'center', padding: '50px' }}>
        <h2>Acceso denegado</h2>
        <p>Debes iniciar sesión para acceder a esta página.</p>
      </div>
    );
  }

  const [showCar, setShowCar] = useState(false); // Estado para controlar la visibilidad de la tarjeta
  const [showPayment, setShowPayment] = useState(false); // Estado para controlar la visibilidad del formulario de pago
  const [showConfirmation, setShowConfirmation] = useState(false); // Controla la visibilidad de la confirmación de reserva
  const [searchData, setSearchData] = useState(null); // Datos del formulario de búsqueda
  const [selectedCar, setSelectedCar] = useState(null); // Coche seleccionado
  const [reservationData, setReservationData] = useState(null); // Datos de la reserva
  const {coches, errorReserva, buscarCochesDisponibles, realizarReserva} = useReserva();
  const [formData, setFormData] = useState({
      pickupOffice: "",
      returnOffice: "",
      pickupDate: "",
      pickupTime: "",
      returnDate: "",
      returnTime: ""
    });


  const handleSearch = async (searchData) => {
    console.log("Search form data:", searchData);
    await buscarCochesDisponibles(formData);
    if(errorReserva) alert("No hay coches disponibles para reservar con estos parametros");
    setShowCar(true); // Mostrar la tarjeta del coche
    setShowPayment(false); // Ocultar el formulario de pago si estaba visible
    setShowConfirmation(false); // Ocultar la confirmación de reserva
    setSearchData(searchData); // Guardar los datos del formulario
  };

  const handleSelectCar = (coche) => {
    setSelectedCar(coche); // Seleccionar el coche
    setShowPayment(true); // Mostrar el formulario de pago
    window.scrollTo({ top: 0, behavior: 'smooth' }); // Scroll al inicio para ver el formulario
  };

  const handleConfirmReservation = (data) => { // ¿No se hace la reserva en la confirmacion de pago?
    setReservationData(data); // Guardar los datos de la reserva
    setShowConfirmation(true); // Mostrar la pantalla de confirmación
  };

  const handleConfirmPayment = async (reservaRealizada) => {
    // Si se realizó la reserva, mostrar confirmación con los datos reales
    if (reservaRealizada) {
      const reservation = {
        ...formData,
        car: selectedCar,
        reservaId: reservaRealizada.id,
        precioFinal: reservaRealizada.precioFinal, // Usar el precio final con descuento
        codigoDescuento: reservaRealizada.codigoDescuento,
        descuentoAplicado: reservaRealizada.descuentoAplicado
      };
      setReservationData(reservation);
      setShowConfirmation(true);
      setShowPayment(false);
    }
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
          <SearchForm onSearch={handleSearch} formData={formData} setFormData={setFormData} />
        )}

        {showCar && !showPayment && !showConfirmation && coches.length > 0 && (
          coches.map((coche) => (
            <TarjetaCocheUI
              key={coche.id}
              coche={coche}
              onSelectCar={() => handleSelectCar(coche)}
            />
          ))
        )}

        {/* Formulario de pago */}
        {showPayment && !showConfirmation && (
          <FormularioPago 
            onConfirmPayment={handleConfirmPayment}
            formData={formData}
            selectedCar={selectedCar}
          />
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