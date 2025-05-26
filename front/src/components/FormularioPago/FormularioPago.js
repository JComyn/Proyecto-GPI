import React, { useState } from "react";
import PropTypes from "prop-types";

// Importamos componentes básicos de Material UI
import {
  Card,
  CardContent,
  Grid,
  TextField,
  Typography,
  InputAdornment,
  Button,
  Alert
} from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';

// Styles
import "./styles.css";
import { realizarReserva } from 'services/reservaService';
import { verificarCodigoDescuento } from 'services/codigoDescuentoService';

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

function FormularioPago({ onConfirmPayment, formData, selectedCar }) {
  const [cardData, setCardData] = useState({
    cardholderName: "",
    cardNumber: "",
    expiryDate: "",
    cvv: "",
    codigoDescuento: ""
  });
  
  const [errors, setErrors] = useState({});
  const [codigoEstado, setCodigoEstado] = useState({
    verificado: false,
    valido: false,
    descuento: 0,
    mensaje: ""
  });
  const [verificandoCodigo, setVerificandoCodigo] = useState(false);

  // AÑADIR: Calcular el precio al inicio del componente
  const dias = calcularDias(formData.pickupDate, formData.returnDate);
  const precioCalculado = calcularPrecio(dias, {
    tarifaDiaria: selectedCar.tarifaDiaria,
    tarifaSemanal: selectedCar.tarifaSemanal,
    tarifaMensual: selectedCar.tarifaMensual,
  });

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

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (validateForm()) {
      try {
        // Calcular el precio (misma lógica que ConfirmacionReserva)
        const dias = calcularDias(formData.pickupDate, formData.returnDate);
        let precioCalculado = calcularPrecio(dias, {
          tarifaDiaria: selectedCar.tarifaDiaria,
          tarifaSemanal: selectedCar.tarifaSemanal,
          tarifaMensual: selectedCar.tarifaMensual,
        });

        // Aplicar descuento si hay código válido
        if (codigoEstado.valido) {
          precioCalculado = precioCalculado * (1 - codigoEstado.descuento / 100);
        }

        // Realizar la reserva con el precio calculado
        const reservaRealizada = await realizarReserva(
          formData, 
          selectedCar.id, 
          cardData.codigoDescuento.trim() || null,
          precioCalculado
        );
        
        console.log("Reserva realizada exitosamente:", reservaRealizada);
        
        // CAMBIO: Pasar el precio final calculado con descuento
        const reservaConPrecio = {
          ...reservaRealizada,
          precioFinal: precioCalculado, // Precio con descuento aplicado
          codigoDescuento: codigoEstado.valido ? cardData.codigoDescuento : null,
          descuentoAplicado: codigoEstado.valido ? codigoEstado.descuento : 0
        };
        
        onConfirmPayment(reservaConPrecio);
        alert("¡Pago procesado correctamente! Reserva confirmada. ¡Gracias por su compra!");
        
      } catch (error) {
        console.error("Error al realizar la reserva:", error);
        alert("Error al procesar la reserva. Por favor, inténtalo de nuevo.");
      }
    }
  };

  // Move helper functions BEFORE they are used
  function calcularDias(fechaInicio, fechaFin) {
      const inicio = new Date(fechaInicio);
      const fin = new Date(fechaFin);
      const diffTime = Math.abs(fin - inicio);
      return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  }

  function calcularPrecio(dias, tarifas) {
      if (!tarifas) return 0;
      if (tarifas.tarifaMensual && dias >= 30) {
        const meses = Math.floor(dias / 30);
        const diasRestantes = dias % 30;
        return meses * tarifas.tarifaMensual + calcularPrecio(diasRestantes, tarifas);
      }
      if (tarifas.tarifaSemanal && dias >= 7) {
        const semanas = Math.floor(dias / 7);
        const diasRestantes = dias % 7;
        return semanas * tarifas.tarifaSemanal + calcularPrecio(diasRestantes, tarifas);
      }
      if (tarifas.tarifaDiaria) {
        return dias * tarifas.tarifaDiaria;
      }
      return 0;
  }

  const verificarCodigo = async () => {
    if (!cardData.codigoDescuento.trim()) {
      setCodigoEstado({
        verificado: false,
        valido: false,
        descuento: 0,
        mensaje: "Introduce un código para verificar"
      });
      return;
    }

    setVerificandoCodigo(true);
    try {
      const codigoData = await verificarCodigoDescuento(cardData.codigoDescuento);
      
      if (codigoData) {
        setCodigoEstado({
          verificado: true,
          valido: true,
          descuento: codigoData.descuento,
          mensaje: `¡Código válido! Descuento del ${codigoData.descuento}%`
        });
      } else {
        setCodigoEstado({
          verificado: true,
          valido: false,
          descuento: 0,
          mensaje: "Código no válido o no existe"
        });
      }
    } catch (error) {
      setCodigoEstado({
        verificado: true,
        valido: false,
        descuento: 0,
        mensaje: "Error al verificar el código"
      });
    } finally {
      setVerificandoCodigo(false);
    }
  };

  // Reset verification when code changes
  const handleCodigoChange = (e) => {
    setCardData({
      ...cardData,
      codigoDescuento: e.target.value.toUpperCase()
    });
    
    // Reset verification state
    setCodigoEstado({
      verificado: false,
      valido: false,
      descuento: 0,
      mensaje: ""
    });
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
              
              {/* Campo de código de descuento con verificación */}
              <Grid item xs={12}>
                <Grid container spacing={1} alignItems="center">
                  <Grid item xs={8}>
                    <TextField
                      label="Código de descuento (opcional)"
                      name="codigoDescuento"
                      value={cardData.codigoDescuento}
                      onChange={handleCodigoChange}
                      fullWidth
                      margin="normal"
                      placeholder="Introduce tu código de descuento"
                      className="payment-input"
                      InputProps={{
                        startAdornment: (
                          <InputAdornment position="start">
                            <LocalOfferIcon />
                          </InputAdornment>
                        ),
                      }}
                    />
                  </Grid>
                  <Grid item xs={4} style={{ paddingTop: '16px' }}>
                    <Button
                      variant="outlined"
                      onClick={verificarCodigo}
                      disabled={verificandoCodigo || !cardData.codigoDescuento.trim()}
                      style={{ height: '56px', width: '100%' }}
                      startIcon={codigoEstado.valido ? <CheckCircleIcon /> : null}
                    >
                      {verificandoCodigo ? "Verificando..." : "Comprobar"}
                    </Button>
                  </Grid>
                </Grid>
                
                {/* Mostrar estado de verificación */}
                {codigoEstado.verificado && (
                  <Alert 
                    severity={codigoEstado.valido ? "success" : "error"}
                    style={{ marginTop: '8px' }}
                  >
                    {codigoEstado.mensaje}
                  </Alert>
                )}
              </Grid>
            </Grid>
            
            {/* Resumen del precio basado en el código de descuento */}
            {codigoEstado.valido && (
              <div style={{ marginTop: '16px', padding: '16px', backgroundColor: '#f5f5f5', borderRadius: '8px' }}>
                <Typography variant="h6">Resumen del precio:</Typography>
                <Typography>Precio original: {precioCalculado} €</Typography>
                <Typography>Descuento ({codigoEstado.descuento}%): -{(precioCalculado * codigoEstado.descuento / 100).toFixed(2)} €</Typography>
                <Typography variant="h6" style={{ color: 'green' }}>
                  Precio final: {(precioCalculado * (1 - codigoEstado.descuento / 100)).toFixed(2)} €
                </Typography>
              </div>
            )}
            
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

// Definir PropTypes para validar las props - CORRECCIÓN
FormularioPago.propTypes = {
  onConfirmPayment: PropTypes.func.isRequired,
  formData: PropTypes.object.isRequired,
  selectedCar: PropTypes.shape({
    id: PropTypes.number.isRequired,
    marca: PropTypes.string,
    modelo: PropTypes.string,
    tarifaDiaria: PropTypes.number.isRequired,    // Añadido
    tarifaSemanal: PropTypes.number.isRequired,   // Añadido
    tarifaMensual: PropTypes.number.isRequired,   // Añadido
    transmision: PropTypes.string,
    categoria: PropTypes.string,
    puertas: PropTypes.number,
    techoSolar: PropTypes.bool,
    extras: PropTypes.arrayOf(PropTypes.string),
  }).isRequired,
  onBack: PropTypes.func, // Función opcional
};

// Valores por defecto para las props
FormularioPago.defaultProps = {
  onBack: () => {}, // Función vacía como valor por defecto
};

export default FormularioPago;