import { useState } from "react";
import { setContext } from "services/contextService";
import {login, registroNegocio, registroParticular} from "services/authService";

export const useAuth = () => {

    const [errorAuth, setErrorAuth] = useState(null);

    const handleLogin = async (email, password) => {
        try {
            const cliente = await login(email, password);
            console.log(cliente);
            setContext(cliente.id, cliente.email);
    
        } catch(error){
            setErrorAuth(error);
            //console.error(error);
        }
    };

    const handleRegistroNegocio = async () => {
        try {
            const cliente = await registroNegocio(nombre, nif, email, password);
            setContext(cliente.id, cliente.email);
    
        } catch(error){
            setErrorAuth(error);
        }
    };

    const handleRegistroParticular = async (nombre, apellidos, direccion, nacimiento, email, password) => {
        try {
            const cliente = await registroParticular(nombre, apellidos, direccion, nacimiento, email, password);
            setContext(cliente.id, cliente.email);
    
        } catch(error){
            setErrorAuth(error);
        }
    };


    return {handleLogin, handleRegistroNegocio, handleRegistroParticular, errorAuth};
};