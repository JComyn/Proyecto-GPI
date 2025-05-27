const API_URL = "http://localhost:8080";

export const getOficinas = async () => {
    console.log("Conectando con back: getOficinas()...");
    try {
        const response = await fetch(`${API_URL}/oficinas`, {
            method: "GET",
            headers: {"Content-Type": "application/json"}
        })

        if(!response.ok) throw new Error("Error en oficinasService.getOficinas()");

        const data = await response.json();
        return data.oficinas;
        
    } catch (error) {
        throw error;
    }

};

export const getOficinaIdByDireccion = async (direccion) => {

    console.log("Conectando con back: getOficinaIdByDireccion()...");
    console.log(direccion);
    try {
        const response = await fetch(`${API_URL}/oficinas/getId`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({direccion: direccion})
        })

        if(!response.ok) throw new Error("Error en oficinasService.getOficinaIdByDireccion()");

        const data = await response.json();
        console.log(data);
        return data;
        
    } catch (error) {
        throw error;
    }

};

export const getOficinaById = async (id) => {
    console.log("Conectando con back: getOficinaById()...");
    try {
        const response = await fetch(`${API_URL}/oficinas/${id}`, {
            method: "GET",
            headers: {"Content-Type": "application/json"}
        })

        if(!response.ok) throw new Error("Error en oficinasService.getOficinaById()");

        const data = await response.json();
        console.log(data);
        return data;
        
    } catch (error) {
        throw error;
    }
};