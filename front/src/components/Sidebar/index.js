import React from 'react';
import './sidebar.css';
import { sidebarData } from './sidebarData';


function Sidebar() {
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
            </ul>
        </div>
    )
}

export default Sidebar;


//componente principal del sidebar