import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Typography, Container, Button } from '@mui/material';

const ViewApartmentPage = () => {
    const [apartment, setApartment] = useState({});
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

    const handleEdit = () => {
        navigate(`./edit`);
    };

    const handleDelete = () => {
        const token = localStorage.getItem('token');
        axios.delete(`http://localhost:8080/api/v1/apartment/delete?apartmentId=${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => {
                // Handle successful deletion
                navigate(`../`); // Redirect to another page after delete
            })
            .catch(error => console.error('Error deleting apartment:', error));
    };


    return (
        <Container>
            <Typography variant="h4">Apartment Details</Typography>
            <Typography variant="h6">Name: {apartment.name}</Typography>
            <Typography variant="body1">Price: {apartment.price}</Typography>
            <Typography variant="body1">Description: {apartment.description}</Typography>
            <Button
                variant="contained"
                color="primary"
                onClick={handleEdit}
                style={{ marginTop: '20px' }}
            >
                Edit Apartment
            </Button>
            <Button
                variant="contained"
                color="error"
                onClick={handleDelete}
                style={{ marginTop: '20px' }}
            >
                Delete Apartment
            </Button>

        </Container>
    );
};

export default ViewApartmentPage;
