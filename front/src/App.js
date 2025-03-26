import React, { useState } from 'react';
import SearchForm from './components/SearchForm';
import TarjetaCocheUI from "./components/TarjetaCoche";
import FormularioPago from "./components/FormularioPago"; // Asegúrate de que el nombre coincida con el archivo exportado
import mockCar from 'components/TarjetaCoche/mockCar';

function App() {
  const [showCar, setShowCar] = useState(false); // Estado para controlar la visibilidad de la tarjeta
  const [showPayment, setShowPayment] = useState(false); // Estado para controlar la visibilidad del formulario de pago
  
  const handleSearch = (searchData) => {
    console.log("Search form data:", searchData);
    setShowCar(true); // Mostrar la tarjeta del coche
    setShowPayment(false); // Ocultar el formulario de pago si estaba visible
  };

  const handleSelectCar = () => {
    setShowPayment(true); // Mostrar el formulario de pago
    window.scrollTo({ top: 0, behavior: 'smooth' }); // Scroll al inicio para ver el formulario
  };

  return (
    <div className="App">
      <main>
        {/* Formulario de búsqueda (se muestra solo si no estamos en pago) */}
        {!showPayment && <SearchForm onSearch={handleSearch} />}

        {/* Mostrar la tarjeta del coche solo si showCar es true y no estamos en pago */}
        {showCar && !showPayment && <TarjetaCocheUI coche={mockCar} onSelectCar={handleSelectCar} />}
        
        {/* Mostrar el formulario de pago solo si showPayment es true */}
        {showPayment && <FormularioPago />}
      </main>
    </div>
  );
}

export default App;