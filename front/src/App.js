import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LandingP from './paginas/landing';
import Login from './paginas/login';
import About from './paginas/aboutus';
import Reservar from './paginas/reservarCoche';
import Sidebar from 'components/Sidebar';

function App() {
    return (
        <div className="App">
            <Sidebar />
            <BrowserRouter>
                <Routes>
                    <Route index element={<LandingP />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/about" element={<About />} />
                    <Route path="/reservar" element={<Reservar />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;