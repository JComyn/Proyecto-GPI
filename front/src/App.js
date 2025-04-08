import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LandingP from './paginas/landing';
import Login from './paginas/login';
import About from './paginas/aboutus';
import Reservar from './paginas/reservarCoche';
import Sidebar from 'components/Sidebar';
import Flota from './paginas/flota';
import RegistroParticularPage from './paginas/registroParticular';

import './App.css';

function App() {
    return (
        <div className="App">
            <Sidebar />
            <div className="content">
            <BrowserRouter>
                <Routes>
                    <Route index element={<LandingP />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/about" element={<About />} />
                    <Route path="/reservar" element={<Reservar />} />
                    <Route path="/flota" element={<Flota />} />
                    <Route path="/registro-particular" element={<RegistroParticularPage />} />
                </Routes>
            </BrowserRouter>
            </div>
        </div>
    );
}

export default App;