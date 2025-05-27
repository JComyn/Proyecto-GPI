import React from 'react';
import './sidebar.css';
import { sidebarData } from './sidebarData';
import { getUserId, getEmail, removeContext } from 'services/contextService';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

function Sidebar() {
    const userId = getUserId();
    const email = getEmail();
    const isAuthenticated = !!userId && userId !== "null" && userId !== "";

    const handleLogout = () => {
        removeContext();
        window.location.reload();
    };

    return (
        <div className="sideBar">
            <h1 className="titulo">AUTO VELOZ</h1>
            <ul className="listaSidebar">
                {sidebarData.map((val, key) => (
                    <li className="fila"
                        key={key}
                        onClick={() => { window.location.pathname = val.link }}>
                        <div className='icono'>{val.icon}</div>
                        <div className='texto'>{val.title}</div>
                    </li>
                ))}
                {isAuthenticated && (
                    <li className="fila" onClick={() => { window.location.pathname = "/mis-reservas"; }}>
                        <div className='icono'><DirectionsCarIcon /></div>
                        <div className='texto'>Mis Reservas</div>
                    </li>
                )}
            </ul>
            {isAuthenticated && (
                <div className="sidebar-user-info">
                    <AccountCircleIcon style={{ fontSize: 32, marginRight: 8 }} />
                    <span>{email}</span>
                    <button className="logout-button" onClick={handleLogout}>
                        Cerrar sesión
                    </button>
                </div>
            )}
        </div>
    )
}

export default Sidebar;


//componente principal del sidebar