const API_URL = "http://localhost:8080";

export const getFlota = async () => {
    console.log("Conectando con back: getFlota()...");
    try {
        const response = await fetch(`${API_URL}/coches`, {
            method: "GET",
            headers: {"Content-Type": "application/json"}
        })

        if(!response.ok) throw new Error("Error en flotaService.getFlota()");

        const data = await response.json();
        return data.coches;
        
    } catch (error) {
        throw error;
    }

};