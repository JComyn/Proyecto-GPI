import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import "./styles.css";
import { useAuth } from "hooks/useAuth";

function RegistroParticular() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nombre: "",
    apellidos: "",
    email: "",
    password: "",
    confirmPassword: "",
    fechaNacimiento: "",
    domicilio: ""
  });

  const {handleRegistroParticular} = useAuth();
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

  const validateForm = () => {
    const newErrors = {};

    // Validar nombre
    if (!formData.nombre.trim()) {
      newErrors.nombre = "El nombre es obligatorio";
    } else if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\s]+$/.test(formData.nombre)) {
      newErrors.nombre = "El nombre no debe contener números ni caracteres especiales";
    }

    // Validar apellidos
    if (!formData.apellidos.trim()) {
      newErrors.apellidos = "Los apellidos son obligatorios";
    } else if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\s]+$/.test(formData.apellidos)) {
      newErrors.apellidos = "Los apellidos no deben contener números ni caracteres especiales";
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

    // Validar fecha de nacimiento
    if (!formData.fechaNacimiento) {
      newErrors.fechaNacimiento = "La fecha de nacimiento es obligatoria";
    }

    // Validar domicilio
    if (!formData.domicilio.trim()) {
      newErrors.domicilio = "El domicilio es obligatorio";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormSubmitted(true);
    
    if (validateForm()) {
      console.log("Datos de registro:", formData);
      const errorAuth = await handleRegistroParticular(formData.nombre, formData.apellidos, formData.domicilio, formData.fechaNacimiento, formData.email, formData.password);
      if(!errorAuth){
        // Mostrar mensaje de éxito
        alert("Registro completado con éxito. Ahora puedes iniciar sesión.");
        // Redirigir al usuario a la página de inicio de sesión
        navigate("/login");
      } else {
        alert("Error: formato incorrecto en la solicitud de registro.");
        // TODO: Lo mismo que en IniciarSesion.js
      }
    }
  };

  return (
    <div className="registro-container">
      <div className="registro-form-container">
        <h2 className="registro-title">Registro de Usuario Particular</h2>
        <p className="registro-subtitle">Completa tus datos personales para crear una cuenta</p>

        <form onSubmit={handleSubmit} className="registro-form">
          <div className="form-grid">
            <div className="form-group">
              <label htmlFor="nombre">Nombre:</label>
              <input
                type="text"
                id="nombre"
                name="nombre"
                value={formData.nombre}
                onChange={handleChange}
                className={errors.nombre && formSubmitted ? 'error' : ''}
                placeholder="Tu nombre"
              />
              {errors.nombre && formSubmitted && <p className="error-message">{errors.nombre}</p>}
            </div>

            <div className="form-group">
              <label htmlFor="apellidos">Apellidos:</label>
              <input
                type="text"
                id="apellidos"
                name="apellidos"
                value={formData.apellidos}
                onChange={handleChange}
                className={errors.apellidos && formSubmitted ? 'error' : ''}
                placeholder="Tus apellidos"
              />
              {errors.apellidos && formSubmitted && <p className="error-message">{errors.apellidos}</p>}
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
                placeholder="ejemplo@correo.com"
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

            <div className="form-group">
              <label htmlFor="fechaNacimiento">Fecha de Nacimiento:</label>
              <input
                type="date"
                id="fechaNacimiento"
                name="fechaNacimiento"
                value={formData.fechaNacimiento}
                onChange={handleChange}
                className={errors.fechaNacimiento && formSubmitted ? 'error' : ''}
              />
              {errors.fechaNacimiento && formSubmitted && <p className="error-message">{errors.fechaNacimiento}</p>}
            </div>

            <div className="form-group">
              <label htmlFor="domicilio">Domicilio:</label>
              <input
                type="text"
                id="domicilio"
                name="domicilio"
                value={formData.domicilio}
                onChange={handleChange}
                className={errors.domicilio && formSubmitted ? 'error' : ''}
                placeholder="Tu dirección completa"
              />
              {errors.domicilio && formSubmitted && <p className="error-message">{errors.domicilio}</p>}
            </div>
          </div>

          <div className="submit-container">
            <button type="submit" className="submit-button">
              Registrarse
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

export default RegistroParticular;