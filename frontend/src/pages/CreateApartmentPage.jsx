import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Typography, Container } from '@mui/material';

const CreateApartmentPage = () => {
    const [apartment, setApartment] = useState({ name: '', price: '', description: '' });
    const navigate = useNavigate();
    const { id } = useParams();

    const handleChange = (event) => {
        setApartment({ ...apartment, [event.target.name]: event.target.value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');
        axios.post('http://localhost:8080/api/v1/apartment/create?portfolioId=' + id, apartment, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => navigate(`/portfolio/${id}`)) // Adjust redirection as necessary
            .catch(error => console.error('Error creating apartment:', error));
    };

    return (
        <Container>
            <Typography variant="h4">Create Apartment</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Apartment Name"
                    name="name"
                    value={apartment.name}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Price"
                    name="price"
                    type="number"
                    value={apartment.price}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Description"
                    name="description"
                    value={apartment.description}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    multiline
                    rows={4}
                />
                <Button type="submit" variant="contained" color="primary">
                    Create
                </Button>
            </form>
        </Container>
    );
};

export default CreateApartmentPage;
