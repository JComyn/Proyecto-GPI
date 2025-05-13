import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import { useAuth } from "hooks/useAuth";
import "./styles.css";

function RegistroNegocio() {
  const navigate = useNavigate();
  const {handleRegistroNegocio, errorAuth} = useAuth();
  const [formData, setFormData] = useState({
    nombreEmpresa: "",
    nif: "",
    email: "",
    password: "",
    confirmPassword: ""
  });

  const [errors, setErrors] = useState({});
  const [formSubmitted, setFormSubmitted] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });

    // Limpiar el error cuando el usuario comienza a escribir
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: null
      });
    }
    
    // Si estamos cambiando la contraseña o la confirmación, verificamos la coincidencia
    if (name === 'password' || name === 'confirmPassword') {
      if (name === 'password' && formData.confirmPassword && value !== formData.confirmPassword) {
        setErrors({
          ...errors,
          confirmPassword: "Las contraseñas no coinciden"
        });
      } else if (name === 'confirmPassword' && formData.password && value !== formData.password) {
        setErrors({
          ...errors,
          confirmPassword: "Las contraseñas no coinciden"
        });
      } else if (name === 'confirmPassword' && formData.password && value === formData.password) {
        setErrors({
          ...errors,
          confirmPassword: null
        });
      }
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  const validateNIF = (nif) => {
    // Validar NIF: una letra seguida de 8 números o 8 números seguidos de una letra
    const nifPattern1 = /^[A-Z][0-9]{8}$/;
    const nifPattern2 = /^[0-9]{8}[A-Z]$/;
    
    return nifPattern1.test(nif) || nifPattern2.test(nif);
  };

  const validateForm = () => {
    const newErrors = {};

    // Validar nombre de la empresa
    if (!formData.nombreEmpresa.trim()) {
      newErrors.nombreEmpresa = "El nombre de la empresa es obligatorio";
    }

    // Validar NIF
    if (!formData.nif.trim()) {
      newErrors.nif = "El NIF es obligatorio";
    } else if (!validateNIF(formData.nif)) {
      newErrors.nif = "El formato del NIF no es válido. Debe ser una letra mayúscula seguida de 8 números o 8 números seguidos de una letra mayúscula";
    }

    // Validar email
    if (!formData.email.trim()) {
      newErrors.email = "El correo electrónico es obligatorio";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = "El formato del correo electrónico no es válido";
    }

    // Validar contraseña
    if (!formData.password) {
      newErrors.password = "La contraseña es obligatoria";
    } else if (formData.password.length < 8) {
      newErrors.password = "La contraseña debe tener al menos 8 caracteres";
    } else if (!/(?=.*[a-zA-Z])(?=.*[0-9])/.test(formData.password)) {
      newErrors.password = "La contraseña debe incluir letras y números";
    }
    
    // Validar confirmación de contraseña
    if (!formData.confirmPassword) {
      newErrors.confirmPassword = "Por favor confirma tu contraseña";
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = "Las contraseñas no coinciden";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormSubmitted(true);
    
    if (validateForm()) {
      console.log("Datos de registro de empresa:", formData);
      const errorAuth = handleRegistroNegocio(formData.nombreEmpresa, formData.nif, formData.email, formData.password);
      if(!errorAuth){
        // Mostrar mensaje de éxito
        alert("Registro de empresa completado con éxito. Ahora puedes iniciar sesión.");
        // Redirigir al usuario a la página de inicio de sesión
        navigate("/login");
      } else {
        alert("Error: formato incorrecto en la solicitud de registro.");
        // TODO: lo mismo que en IniciarSesion.js
      }
      
    }
  };

  return (
    <div className="registro-container">
      <div className="registro-form-container">
        <h2 className="registro-title">Registro de Usuario Negocio</h2>
        <p className="registro-subtitle">Completa los datos de tu empresa para crear una cuenta</p>

        <form onSubmit={handleSubmit} className="registro-form">
          <div className="form-grid">
            <div className="form-group">
              <label htmlFor="nombreEmpresa">Nombre de la Empresa:</label>
              <input
                type="text"
                id="nombreEmpresa"
                name="nombreEmpresa"
                value={formData.nombreEmpresa}
                onChange={handleChange}
                className={errors.nombreEmpresa && formSubmitted ? 'error' : ''}
                placeholder="Nombre de tu empresa"
              />
              {errors.nombreEmpresa && formSubmitted && (
                <p className="error-message">{errors.nombreEmpresa}</p>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="nif">NIF:</label>
              <input
                type="text"
                id="nif"
                name="nif"
                value={formData.nif}
                onChange={handleChange}
                className={errors.nif && formSubmitted ? 'error' : ''}
                placeholder="Ejemplo: A12345678 o 12345678B"
              />
              {errors.nif && formSubmitted && <p className="error-message">{errors.nif}</p>}
            </div>

            <div className="form-group">
              <label htmlFor="email">Correo Electrónico:</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className={errors.email && formSubmitted ? 'error' : ''}
                placeholder="empresa@dominio.com"
              />
              {errors.email && formSubmitted && <p className="error-message">{errors.email}</p>}
            </div>

            <div className="form-group password-group">
              <label htmlFor="password">Contraseña:</label>
              <div className="password-input-container">
                <input
                  type={showPassword ? "text" : "password"}
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  className={errors.password && formSubmitted ? 'error' : ''}
                  placeholder="Mínimo 8 caracteres"
                />
                <span className="password-toggle" onClick={togglePasswordVisibility}>
                  {showPassword ? <VisibilityOffIcon /> : <VisibilityIcon />}
                </span>
              </div>
              {errors.password && formSubmitted && <p className="error-message">{errors.password}</p>}
            </div>

            <div className="form-group password-group">
              <label htmlFor="confirmPassword">Confirmar Contraseña:</label>
              <div className="password-input-container">
                <input
                  type={showConfirmPassword ? "text" : "password"}
                  id="confirmPassword"
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  className={errors.confirmPassword && formSubmitted ? 'error' : ''}
                  placeholder="Repite tu contraseña"
                />
                <span className="password-toggle" onClick={toggleConfirmPasswordVisibility}>
                  {showConfirmPassword ? <VisibilityOffIcon /> : <VisibilityIcon />}
                </span>
              </div>
              {errors.confirmPassword && formSubmitted && (
                <p className="error-message">{errors.confirmPassword}</p>
              )}
            </div>
          </div>

          <div className="submit-container">
            <button type="submit" className="submit-button">
              Registrar empresa
            </button>
          </div>

          <div className="login-link">
            ¿Ya tienes cuenta? <a href="/login">Inicia sesión aquí</a>
          </div>
        </form>
      </div>
    </div>
  );
}

export default RegistroNegocio;