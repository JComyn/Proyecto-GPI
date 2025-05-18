import React from 'react';
import './sidebar.css';
import { sidebarData } from './sidebarData';
import { getUserId } from 'services/contextService';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';

function Sidebar() {
    const isAuthenticated = !!getUserId(); // Verifica si el usuario está autenticado

    return (
        <div className="sideBar">

            <h1 className="titulo">AUTO VELOZ</h1>
            <ul className="listaSidebar">
            {sidebarData.map((val, key) => {
                return (
                    <li className="fila"
                    key={key} onClick = {() => {window.location.pathname = val.link}}>

                        <div className='icono'>{val.icon}</div>
                        <div className='texto'>{val.title}</div>
                    </li>
                );
            })}
            {isAuthenticated && (
                <li className="fila" onClick={() => { window.location.pathname = "/mis-reservas"; }}>
                    <div className='icono'><DirectionsCarIcon /></div>
                    <div className='texto'>Mis Reservas</div>
                </li>
            )}
            </ul>
        </div>
    )
}

export default Sidebar;


//componente principal del sidebar