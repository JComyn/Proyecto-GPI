import React from 'react'
import HomeIcon from '@mui/icons-material/Home';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import HelpIcon from '@mui/icons-material/Help';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import GarageIcon from '@mui/icons-material/Garage'; // Importa el icono de Garage


export const sidebarData = [
    {
        title: "Inicio",
        icon: <HomeIcon />,
        link: "/"
    },
    {
        title: "Iniciar Sesión",
        icon: <AccountCircleIcon />,
        link: "/login"
    },
    {
        title: "¿Quiénes somos?",
        icon: <HelpIcon />,
        link: "/about"
    },
    {

        title: "Reservar",
        icon: <DirectionsCarIcon />,
        link: "/reservar"
    },
    {

        title: "Visualizar flota",
        icon: <GarageIcon />,
        link: "/flota"
    }
];
