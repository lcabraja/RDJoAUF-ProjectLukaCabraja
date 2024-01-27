import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { List, ListItem, ListItemText, Button, Typography } from '@mui/material';

const ViewPortfolioPage = () => {
    const [portfolio, setPortfolio] = useState({ id: -1, name: '', apartments: [] });
    const { id } = useParams();
    const navigate = useNavigate();

    const handleFetch = (id) => {
        const token = localStorage.getItem('token');
        axios.get(`http://localhost:8080/api/v1/portfolio/get?portfolioId=${id}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(response => setPortfolio(response.data))
            .catch(error => console.error('Error fetching portfolio:', error));
    }

    useEffect(() => {
        handleFetch(id);
    }, [id]);

    const handleDelete = (id) => {
        const token = localStorage.getItem('token');
        axios.delete(`http://localhost:8080/api/v1/apartment/delete?apartmentId=${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => {
                handleFetch(id);
            })
            .catch(error => console.error('Error deleting apartment:', error));
    };

    return (
        <div>
            <Typography variant="h4">Portfolio: {portfolio.name}</Typography>
            <Button
                variant="contained"
                color="primary"
                style={{ marginBottom: '20px' }}
                onClick={() => navigate(`/portfolio/${portfolio.id}/edit`)}
            >
                Edit Portfolio
            </Button>
            <Button
                variant="contained"
                color="primary"
                style={{ marginBottom: '20px' }}
                onClick={() => navigate(`/portfolio/${portfolio.id}/add`)}
            >
                Add Apartment
            </Button>
            <List>
                {portfolio.apartments.map((apartment) => (
                    <ListItem key={apartment.id}>
                        <ListItemText primary={apartment.name} secondary={`Price: ${apartment.price}`} />
                        <Button onClick={() => navigate(`/apartment/${apartment.id}`)}>View</Button>
                        <Button onClick={() => navigate(`/apartment/${apartment.id}/edit`)}>Edit</Button>
                        <Button onClick={() => handleDelete(apartment.id)}>Delete</Button>
                    </ListItem>
                ))}
            </List>
        </div>
    );
};

export default ViewPortfolioPage;
