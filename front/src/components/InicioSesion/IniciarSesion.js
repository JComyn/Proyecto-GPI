import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./IniciarSesion.css";
import { useAuth } from "hooks/useAuth";

function IniciarSesion() {
  const {handleLogin} = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateEmail(email)) {
      setError("Por favor, introduce un correo válido.");
      return;
    }
    if (!password) {
      setError("Por favor, introduce tu contraseña.");
      return;
    }
    setError("");
    console.log("Iniciando sesión con:", { email, password });
    const errorAuth = await handleLogin(email, password);
    if(!errorAuth){
      alert("Sesión iniciada con éxito, se te redirigira a inicio.");
      navigate("/");
      window.location.reload();
    }
    else{
      alert("Credenciales incorrectas");
    } // TODO: Aqui convendria llamar a un handleError mas sofisticado
    
  };

  return (
    <div className = "claseparacentrar">
    <div className="login-container">
      <h2 className="confirmation-title">Iniciar Sesión</h2>
      <form onSubmit={handleSubmit} className="login-form">
        <div className="form-group">
          <label htmlFor="email">Correo Electrónico:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="usuario@dominio.com"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Contraseña:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Introduce tu contraseña"
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        <button type="submit" className="login-button">
          Iniciar Sesión
        </button>
      </form>
      <div className="register-buttons">
        <button
          className="register-button"
          onClick={() => navigate("/registro-particular")}
        >
          Registro como Particular
        </button>
        <button
          className="register-button"
          onClick={() => navigate("/registro-negocio")}
        >
          Registro como Negocio
        </button>
      </div>
    </div>
    </div>
  );
}

export default IniciarSesion;