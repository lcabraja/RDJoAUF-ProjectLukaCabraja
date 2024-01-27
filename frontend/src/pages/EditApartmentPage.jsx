import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Typography, Container } from '@mui/material';

const EditApartmentPage = () => {
    const [apartment, setApartment] = useState({ name: '', price: '', description: '' });
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get(`http://localhost:8080/api/v1/apartment/get?apartmentId=${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => setApartment(response.data))
            .catch(error => console.error('Error fetching apartment:', error));
    }, [id]);

    const handleSubmit = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');
        axios.put(`http://localhost:8080/api/v1/apartment/update`, apartment, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => navigate(`/apartment/${id}`))
            .catch(error => console.error('Error updating apartment:', error));
    };

    const handleChange = (event) => {
        setApartment({ ...apartment, [event.target.name]: event.target.value });
    };

    return (
        <Container>
            <Typography variant="h4">Edit Apartment</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Name"
                    name="name"
                    value={apartment.name}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Price"
                    name="price"
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
                />
                {/* Include other fields as necessary */}
                <Button type="submit" variant="contained" color="primary">
                    Save Changes
                </Button>
            </form>
        </Container>
    );
};

export default EditApartmentPage;
