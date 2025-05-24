import { getUserId } from "./contextService";

const API_URL = "http://localhost:8080";

function combinarFechaYHora(fecha, hora) {
    if (!fecha || !hora) return null;
    return `${fecha}T${hora}`;
  }
  
  export async function obtenerCochesDisponibles(formData) {
    const requestBody = {
      idOficinaRecogida: parseInt(formData.pickupOffice),
      fechaRecogida: combinarFechaYHora(formData.pickupDate, formData.pickupTime),
      fechaDevolucion: combinarFechaYHora(formData.returnDate, formData.returnTime)
    };
  
    try {
      const response = await fetch(`${API_URL}/reservas/disponibles`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody)
      });
  
      if (!response.ok) {
        throw new Error("Error al obtener coches disponibles");
      }
  
      const data = await response.json(); // esto será ListaCoches con la lista interna
      return data.coches || []; // asumiendo que `ListaCoches` tiene un campo `coches`
    } catch (error) {
      console.error("Error al llamar al backend:", error);
      throw error;
    }
  };

  export const realizarReserva = async (formData, idCoche) => {
    const requestBody = {
        idCoche: idCoche,
        idCliente: getUserId(),
        idOficinaRecogida: parseInt(formData.pickupOffice),
        idOficinaDevolucion: parseInt(formData.returnOffice),
        fechaRecogida: combinarFechaYHora(formData.pickupDate, formData.pickupTime),
        fechaDevolucion: combinarFechaYHora(formData.returnDate, formData.returnTime)
    };

    try {
        const response = await fetch(`${API_URL}/reservas/realizar`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(requestBody)
        });
    
        if (!response.ok) {
          throw new Error("Error al realizar reserva");
        }
    
        const data = await response.json(); // esto será Reserva
        return data
      } catch (error) {
        console.error("Error al llamar al backend:", error);
        throw error;
      }

  };

  export const obtenerReservas = async () => {
    const response = await fetch("http://localhost:8080/reservas");
    if (!response.ok) throw new Error("Error al obtener reservas");
    return (await response.json()).reservas || [];
};
