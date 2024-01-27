import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        // Clear the JWT token from local storage
        localStorage.removeItem('token'); // Adjust the key if different
        // Redirect to the login page
        navigate('/login');
    }, [navigate]);

    // Optionally, display a message or a loader while redirecting
    return <div>Logging out...</div>;
};

export default Logout;
