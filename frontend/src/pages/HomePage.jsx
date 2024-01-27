import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Grid, Paper, Avatar, Typography, List, ListItem, ListItemText, ListItemButton } from '@mui/material';

const HomePage = () => {
    const [user, setUser] = useState({
        id: 2,
        firstName: "Mislav",
        lastName: "Marinović",
        username: "devuser",
        language: "hr",
        password: "$2a$10$OR/5yybw6gbbX7Rk2V9RqOJPQQxhJlT0wIfD5FvwEONRS4WvMN/8.",
        role: "USER",
        portfolios: [],
        enabled: true,
        accountNonExpired: true,
        accountNonLocked: true,
        authorities: [
            {
                authority: "ROLE_USER"
            }
        ],
        credentialsNonExpired: true
    });
    const [portfolios, setPortfolios] = useState([]);

    async function fetchUser() {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get('http://localhost:8080/api/v1/auth/valid', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            setUser(response.data);
            return true;
        } catch (error) {
            return false;
        }
    }
    async function fetchData() {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get('http://localhost:8080/api/v1/portfolio/all', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setPortfolios(response.data);
        } catch (error) {
            console.error('Error fetching portfolios:', error);
        }
    }

    useEffect(() => {
        fetchUser();
        fetchData();
    }, []);

    const handleDelete = (id) => {
        const token = localStorage.getItem('token');
        axios.delete(`http://localhost:8080/api/v1/portfolio/delete?portfolioId=${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => {
                fetchData();
            })
            .catch(error => console.error('Error deleting apartment:', error));
    };


    return (
        <Container style={{ height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Grid container spacing={2} style={{ maxWidth: 900 }}>
                <Grid item xs={6}>
                    <Paper style={{ padding: 20, textAlign: 'center', height: '100%' }}>
                        <Avatar style={{ margin: 'auto', width: 60, height: 60 }} />
                        <Typography variant="h6">{user.firstName} {user.lastName}</Typography>
                        <Typography variant="body1">@{user?.username}</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={6}>
                    <Paper style={{ padding: 20, height: '100%' }}>
                        <Typography variant="h6">Portfolios</Typography>
                        <ListItemButton component="a" href={`/portfolio/new`}>
                            <ListItemText primary="Create Porfolio" />
                        </ListItemButton>
                        <List>
                            {portfolios.map((portfolio, index) => (
                                <ListItem key={index}>
                                    <ListItemText primary={`${portfolio.name} [${portfolio.id}]`} secondary={`Apartments: ${portfolio.apartments.length}`} />
                                    <ListItemButton component="a" href={`/portfolio/${portfolio.id}`}>
                                        <ListItemText primary="View" />
                                    </ListItemButton>
                                    <ListItemButton component="a" href={`/portfolio/${portfolio.id}/edit`}>
                                        <ListItemText primary="Edit" />
                                    </ListItemButton>
                                    <ListItemButton component="button" onClick={() => handleDelete(portfolio.id)}>
                                        <ListItemText primary="Delete" />
                                    </ListItemButton>
                                </ListItem>
                            ))}
                        </List>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default HomePage;
