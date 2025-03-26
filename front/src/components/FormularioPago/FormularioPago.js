import React, { useState } from "react";
import PropTypes from "prop-types";

// Importamos componentes básicos de Material UI sin usar los MD* personalizados
import {
  Card,
  CardContent,
  Grid,
  TextField,
  Typography,
  InputAdornment
} from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";

// Styles
import "./styles.css";

// Funciones de validación
const validateCardNumber = (cardNumber) => {
  const digitsOnly = cardNumber.replace(/\s/g, "");
  return digitsOnly.length === 16 && /^\d+$/.test(digitsOnly);
};

const formatCardNumber = (value) => {
  // Remove all non-digit characters
  const digitsOnly = value.replace(/\D/g, "");
  
  // Insert a space after every 4 digits
  let formattedNumber = "";
  for (let i = 0; i < digitsOnly.length; i++) {
    if (i > 0 && i % 4 === 0) {
      formattedNumber += " ";
    }
    formattedNumber += digitsOnly[i];
  }
  
  return formattedNumber;
};

// Nueva función para validar que la fecha de expiración es posterior a la actual
const validateExpiryDate = (expiryDate) => {
  // Verificar formato MM/YY
  if (!/^\d{2}\/\d{2}$/.test(expiryDate)) {
    return false;
  }
  
  const [month, year] = expiryDate.split('/');
  
  // Crear fecha con el último día del mes de expiración
  // Añadimos 2000 al año para obtener el año completo (20xx)
  const expiry = new Date(2000 + parseInt(year), parseInt(month), 0);
  
  // Fecha actual
  const today = new Date();
  
  // Comparar solo el mes y el año
  if (
    expiry.getFullYear() < today.getFullYear() || 
    (expiry.getFullYear() === today.getFullYear() && 
     expiry.getMonth() < today.getMonth())
  ) {
    return false;
  }
  
  return true;
};

function FormularioPago({ onBack }) {
  const [cardData, setCardData] = useState({
    cardNumber: "",
    cardholderName: "",
    expiryDate: "",
    cvv: "",
  });
  
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === "cardNumber") {
      const formattedValue = formatCardNumber(value);
      setCardData({
        ...cardData,
        [name]: formattedValue,
      });
    } else if (name === "expiryDate") {
      // Formato MM/YY para fecha de expiración
      const formatted = value
        .replace(/\D/g, "")
        .replace(/^(\d{2})(\d)/, "$1/$2")
        .slice(0, 5);
      
      setCardData({
        ...cardData,
        [name]: formatted,
      });
    } else {
      setCardData({
        ...cardData,
        [name]: value,
      });
    }
    
    // Clear error when user types
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: "",
      });
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!cardData.cardholderName.trim()) {
      newErrors.cardholderName = "El nombre del titular es obligatorio";
    }
    
    if (!validateCardNumber(cardData.cardNumber)) {
      newErrors.cardNumber = "El número de tarjeta debe tener 16 dígitos";
    }
    
    if (!cardData.expiryDate.trim()) {
      newErrors.expiryDate = "La fecha de expiración es obligatoria";
    } else if (!/^\d{2}\/\d{2}$/.test(cardData.expiryDate)) {
      newErrors.expiryDate = "Formato inválido. Use MM/YY";
    } else if (!validateExpiryDate(cardData.expiryDate)) {
      newErrors.expiryDate = "La tarjeta ha caducado o la fecha no es válida";
    }
    
    if (!cardData.cvv.trim()) {
      newErrors.cvv = "El código de seguridad es obligatorio";
    } else if (!/^\d{3,4}$/.test(cardData.cvv)) {
      newErrors.cvv = "El CVV debe tener 3 o 4 dígitos";
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (validateForm()) {
      // Process payment here in a real application
      console.log("Procesando pago:", cardData);
      
      // Mostrar mensaje de confirmación
      alert("¡Pago procesado correctamente! Reserva confirmada. ¡Gracias por su compra!");
      
      // Reiniciar la aplicación
      window.location.reload();
    }
  };

  return (
    <div className="payment-container">
      <Typography variant="h4" className="payment-title">
        Información de Pago
      </Typography>
      
      <Typography variant="subtitle1" className="payment-subtitle">
        Tras seleccionar un coche, complete los datos de su tarjeta.
      </Typography>
      
      <Card className="payment-card">
        <div className="payment-header">
          <Typography variant="h5" className="payment-header-title">
            Detalles de la Tarjeta
          </Typography>
        </div>
        
        <CardContent>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  label="Nombre del titular"
                  name="cardholderName"
                  value={cardData.cardholderName}
                  onChange={handleChange}
                  fullWidth
                  margin="normal"
                  error={!!errors.cardholderName}
                  helperText={errors.cardholderName}
                  className="payment-input"
                />
              </Grid>
              
              <Grid item xs={12}>
                <TextField
                  label="Número de tarjeta"
                  name="cardNumber"
                  value={cardData.cardNumber}
                  onChange={handleChange}
                  fullWidth
                  margin="normal"
                  inputProps={{ maxLength: 19 }}
                  error={!!errors.cardNumber}
                  helperText={errors.cardNumber}
                  className="payment-input"
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <CreditCardIcon />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              
              <Grid item xs={6}>
                <TextField
                  label="Fecha de expiración (MM/YY)"
                  name="expiryDate"
                  value={cardData.expiryDate}
                  onChange={handleChange}
                  fullWidth
                  margin="normal"
                  inputProps={{ maxLength: 5 }}
                  error={!!errors.expiryDate}
                  helperText={errors.expiryDate}
                  className="payment-input"
                />
              </Grid>
              
              <Grid item xs={6}>
                <TextField
                  label="CVV"
                  name="cvv"
                  value={cardData.cvv}
                  onChange={handleChange}
                  fullWidth
                  margin="normal"
                  inputProps={{ maxLength: 4 }}
                  error={!!errors.cvv}
                  helperText={errors.cvv}
                  className="payment-input"
                />
              </Grid>
            </Grid>
            
            <div className="submit-container">
              <button type="submit" className="submit-button">
                Confirmar Pago
              </button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}

// Definir PropTypes para validar las props
FormularioPago.propTypes = {
  onBack: PropTypes.func, // Definir onBack como una función opcional
};

// Valores por defecto para las props
FormularioPago.defaultProps = {
  onBack: () => {}, // Función vacía como valor por defecto
};

export default FormularioPago;