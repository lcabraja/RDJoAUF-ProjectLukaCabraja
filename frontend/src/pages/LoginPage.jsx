import React, { useState } from 'react';
import { TextField, Button, Grid, Link, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const LoginPage = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const navigate = useNavigate();

    const [errorMessage, setErrorMessage] = useState('');

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/login', credentials);
            console.log(response.data);
            localStorage.setItem('token', response.data.token);
            navigate('/home');
            
        } catch (error) {
            console.error('Login failed:', error);
            localStorage.removeItem('token');
            setErrorMessage("Error while logging in: " + error.response.data.forbidden);
        }
    };

    document.title = 'Login';

    return (
        <Container maxWidth="xs">
            <Typography variant="h5">Login</Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Username"
                            name="username"
                            value={credentials.username}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Password"
                            name="password"
                            type="password"
                            value={credentials.password}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth onClick={handleSubmit} variant="contained">
                            Login
                        </Button>
                    </Grid>
                    <Grid item>
                        <Link href="#" onClick={() => navigate('/register')}>
                            Don't have an account? Register
                        </Link>
                    </Grid>
                </Grid>
            </form>
            { errorMessage && <Typography paddingTop={"4px"} color="error">{errorMessage}</Typography> }
        </Container>
    );
};

export default LoginPage;
