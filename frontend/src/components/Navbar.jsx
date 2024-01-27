// src/components/Navbar.jsx

import React from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Select, MenuItem } from '@mui/material';
import i18n from 'i18next';
import { useTranslation } from 'react-i18next';

const Navbar = () => {
    const changeLanguage = (event) => {
        i18n.changeLanguage(event.target.value);
    };

    const { t } = useTranslation();

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" style={{ flexGrow: 1 }}>
                    Dynasty8
                </Typography>
                <Button color="inherit" component={Link} to="/">{t('home')}</Button>
                <Button color="inherit" component={Link} to="/login">{t('login')}</Button>
                <Button color="inherit" component={Link} to="/logout">{t('logout')}</Button>
                <Select
                    value={i18n.language}
                    onChange={changeLanguage}
                    style={{ color: 'white', marginLeft: 'auto' }}
                >
                    <MenuItem value="en">English</MenuItem>
                    <MenuItem value="hr">Hrvatski</MenuItem>
                </Select>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
