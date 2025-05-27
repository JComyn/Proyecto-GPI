const API_URL = "http://localhost:8080/clientes"

export const login = async (email, password) => {

    try {
        const response = await fetch(`${API_URL}/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({email: email, password: password}),
        })
        if(!response.ok){
            throw new Error("Error en authService.login()");
        }
        return await response.json()
        
    } catch (error) {
        throw error;
    }

}

export const registroNegocio = async (nombre, nif, email, password) => {

    try {
        const response = await fetch(`${API_URL}/registro/negocio`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({nombre: nombre, email: email, password: password, nif: nif})
        })
        if(!response.ok) throw new Error("Error en authService.registroNegocio()");
        return await response.json()
        
    } catch (error) {
        throw error;
    }

};

export const registroParticular = async (nombre, apellidos, direccion, nacimiento, email, password) => {

    try {
        const response = await fetch(`${API_URL}/registro/particular`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({nombre: nombre, apellidos: apellidos, email: email, password: password, direccion: direccion, nacimiento: nacimiento})
        })
        if(!response.ok) throw new Error("Error en authService.registroParticular()");
        return await response.json()
        
    } catch (error) {
        throw error;
    }

};