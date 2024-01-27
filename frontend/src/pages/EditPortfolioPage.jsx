import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Typography, Container } from '@mui/material';

const EditPortfolioPage = () => {
    const [portfolio, setPortfolio] = useState({ id: -1, name: '' });
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get(`http://localhost:8080/api/v1/portfolio/get?portfolioId=${id}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(response => { console.log(response.data); setPortfolio(response.data); })
            .catch(error => console.error('Error fetching portfolio:', error));
    }, [id]);

    const handleSubmit = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');
        axios.put(`http://localhost:8080/api/v1/portfolio/update`, portfolio, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(() => navigate(`/portfolio/${id}`))
            .catch(error => console.error('Error updating portfolio:', error));
    };

    const handleChange = (event) => {
        setPortfolio({ ...portfolio, [event.target.name]: event.target.value });
    };

    return (
        <Container>
            <Typography variant="h4">Edit Portfolio</Typography>
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
                    Save Changes
                </Button>
            </form>
        </Container>
    );
};

export default EditPortfolioPage;
