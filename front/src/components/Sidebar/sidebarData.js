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
        title: "Profile",
        icon: <AccountCircleIcon />,
        link: "/login"
    },
    {
        title: "About us",
        icon: <HelpIcon />,
        link: "/about"
    },
    {

        title: "Reservar",
        icon: <HelpIcon />,
        link: "/reservar"
    }
];
