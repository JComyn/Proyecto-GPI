import { useState } from "react";
import { obtenerCochesDisponibles } from "../services/reservaService";

export function useReserva() {
  const [coches, setCoches] = useState([]);
  const [loading, setLoading] = useState(false);
  const [errorReserva, setErrorReserva] = useState(null);

  const buscarCochesDisponibles = async (formData) => {
    setLoading(true);
    setErrorReserva(null);
    try {
        const resultado = await obtenerCochesDisponibles(formData);
        setCoches(resultado);
    } catch (err) {
        setErrorReserva("Ocurrió un error al buscar coches disponibles.");
    } finally {
        setLoading(false);
    }
  };

  const realizarReserva = async (formData, idCoche) => {
    try {
        const resultado = await realizarReserva(formData, idCoche);
        return await resultado.json();
    } catch (err) {
        setErrorReserva("Ocurrió un error al realizar la reserva.");
    }
  };

  return {
    coches,
    loading,
    errorReserva,
    buscarCochesDisponibles,
    realizarReserva
  };
}
