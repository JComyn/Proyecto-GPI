import { useState } from "react";
import { setContext } from "services/contextService";
import {login, registroNegocio, registroParticular} from "services/authService";

export const useAuth = () => {

    const handleLogin = async (email, password) => {
        try {
            const cliente = await login(email, password);
            setContext(cliente.id, cliente.email);
            return null;
    
        } catch(error){
            return error;
        }
    };

    const handleRegistroNegocio = async () => {
        try {
            const cliente = await registroNegocio(nombre, nif, email, password);
            setContext(cliente.id, cliente.email);
            return null;
        } catch(error){
            return error;
        }
    };

    const handleRegistroParticular = async (nombre, apellidos, direccion, nacimiento, email, password) => {
        try {
            const cliente = await registroParticular(nombre, apellidos, direccion, nacimiento, email, password);
            setContext(cliente.id, cliente.email);
            return null;
    
        } catch(error){
            return error;
        }
    };


    return {handleLogin, handleRegistroNegocio, handleRegistroParticular};
};