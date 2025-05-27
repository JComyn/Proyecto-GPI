const API_URL = "http://localhost:8080";

export const obtenerCodigosDescuento = async () => {
    try {
        const response = await fetch(`${API_URL}/codigosDescuento`);
        if (!response.ok) {
            throw new Error("Error al obtener códigos de descuento");
        }
        const data = await response.json();
        return data.codigosDescuento || [];
    } catch (error) {
        console.error("Error al llamar al backend:", error);
        throw error;
    }
};

export const verificarCodigoDescuento = async (codigo) => {
    try {
        const codigos = await obtenerCodigosDescuento();
        return codigos.find(c => c.codigo === codigo.toUpperCase());
    } catch (error) {
        console.error("Error al verificar código:", error);
        return null;
    }
};