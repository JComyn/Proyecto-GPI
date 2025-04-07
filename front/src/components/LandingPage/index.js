import React from 'react';
import './landingPage.css';

const LandingPage = () => {
    return (
        <div className="landing-page">
            <header className="landing-header">
                <div className="subtitulo">Bienvenido a</div>
                <h1 className= "titulo">Auto Veloz</h1>
                <button className="cta-button" onClick = {() => {window.location.pathname = "/reservar"}}>Reservar</button>
            </header>
        </div>
    );
};

export default LandingPage;