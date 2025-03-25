













import React, { useState } from 'react';
import SearchForm from './components/SearchForm';
import TarjetaCocheUI from "./components/TarjetaCoche";
import mockCar from 'components/TarjetaCoche/mockCar';
import Sidebar from 'components/Sidebar';

function App() {
  const [showCar, setShowCar] = useState(false); // Estado para controlar la visibilidad de la tarjeta
  
  const handleSearch = (searchData) => {
    console.log("Search form data:", searchData);
    alert("Search successful! Redirecting to available cars would happen here.");
    setShowCar(true); // Mostrar la tarjeta del coche
  };

  return (
    <div className="App">


      <Sidebar />
      <main>
        
        {/* Formulario de búsqueda */}
        <SearchForm onSearch={handleSearch} />

        {/* Mostrar la tarjeta del coche solo si showCar es true */}
        {showCar && <TarjetaCocheUI coche={mockCar} />}
        

      </main>
    </div>
  );
}

export default App;