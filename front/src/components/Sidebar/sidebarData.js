import React from 'react'
import HomeIcon from '@mui/icons-material/Home';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import HelpIcon from '@mui/icons-material/Help';

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
        icon: <HelpIcon />,
        link: "/reservar"
    }
];
