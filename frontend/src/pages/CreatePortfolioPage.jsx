import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Typography, Container } from '@mui/material';

const CreatePortfolioPage = () => {
    const [portfolio, setPortfolio] = useState({ name: '', apartments: [] });
    const navigate = useNavigate();

    const handleChange = (event) => {
        setPortfolio({ ...portfolio, [event.target.name]: event.target.value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');
        axios.post('http://localhost:8080/api/v1/portfolio/create', portfolio, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => navigate('/')) // Adjust redirection as necessary
            .catch(error => console.error('Error creating portfolio:', error));
    };

    return (
        <Container>
            <Typography variant="h4">Create Portfolio</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Portfolio Name"
                    name="name"
                    value={portfolio.name}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                />
                <Button type="submit" variant="contained" color="primary">
                    Create
                </Button>
            </form>
        </Container>
    );
};

export default CreatePortfolioPage;
