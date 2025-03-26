import React from 'react';
import './landingPage.css';

const LandingPage = () => {
    return (
        <div className="landing-page">
            <header className="landing-header">
                <h1>Bienvenido a Car Rent</h1>
                <button className="cta-button" onClick = {() => {window.location.pathname = "/reservar"}}>Get Started</button>
            </header>
        </div>
    );
};

export default LandingPage;