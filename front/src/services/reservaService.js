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

  export const realizarReserva = async (formData, idCoche, codigoDescuento = null, precioCalculado = null) => {
    const requestBody = {
        idCoche: idCoche,
        idCliente: parseInt(getUserId()),
        idOficinaRecogida: parseInt(formData.pickupOffice),
        idOficinaDevolucion: parseInt(formData.returnOffice),
        fechaHoraRecogida: combinarFechaYHora(formData.pickupDate, formData.pickupTime),
        fechaHoraDevolucion: combinarFechaYHora(formData.returnDate, formData.returnTime),
        tipoTarifa: "DIARIA_KILOMETRAJE",
        codigoDescuento: codigoDescuento || null,
        precioCalculado: precioCalculado  // <-- Añadir este campo
    };

    try {
        const response = await fetch(`${API_URL}/reservas/realizar`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            const errorData = await response.text();
            throw new Error(`Error al realizar reserva: ${errorData}`);
        }

        const data = await response.json();
        return data.reserva || data;
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
