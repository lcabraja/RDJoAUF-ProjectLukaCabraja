import React, { useState } from 'react';
import { TextField, Button, Grid, Link, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RegisterPage = () => {
    const [userInfo, setUserInfo] = useState({
        username: '',
        firstName: '',
        lastName: '',
        password: '',
        language: ''
    });
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUserInfo({ ...userInfo, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/register', userInfo);
            localStorage.setItem('token', response.data.token);
            navigate('/home');

        } catch (error) {
            console.error('Registration failed:', error);
            setErrorMessage("Error while registering: " + error.response.data);
        }
    };

    document.title = 'Register';

    return (
        <Container maxWidth="xs">
            <Typography variant="h5">Register</Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Username"
                            name="username"
                            value={userInfo.username}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="First Name"
                            name="firstName"
                            value={userInfo.firstName}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Last Name"
                            name="lastName"
                            value={userInfo.lastName}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Password"
                            name="password"
                            type="password"
                            value={userInfo.password}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Language"
                            name="language"
                            value={userInfo.language}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth type="submit" variant="contained">
                            Register
                        </Button>
                    </Grid>
                    <Grid item>
                        <Link href="#" onClick={() => navigate('/login')}>
                            Already have an account? Login
                        </Link>
                    </Grid>
                </Grid>
            </form>
            { errorMessage && <Typography paddingTop={"4px"} color="error">{errorMessage}</Typography> }
        </Container>
    );
};

export default RegisterPage;
