import React from 'react';
import { Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import LogoutPage from './pages/LogoutPage';
import RegisterPage from './pages/RegisterPage';
import ViewPortfolioPage from './pages/ViewPortfolioPage';
import EditPortfolioPage from './pages/EditPortfolioPage';
import CreatePortfolioPage from './pages/CreatePortfolioPage';
import ViewApartmentPage from './pages/ViewApartmentPage';
import EditApartmentPage from './pages/EditApartmentPage';
import CreateApartmentPage from './pages/CreateApartmentPage';
import isAuthenticated from './utils/isAuthenticated';

const App = () => {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Navigate to={isAuthenticated() ? "/home" : "/login"} />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/logout" element={<LogoutPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/portfolio/:id" element={<ViewPortfolioPage />} />
          <Route path="/portfolio/new" element={<CreatePortfolioPage />} />
          <Route path="/portfolio/:id/edit" element={<EditPortfolioPage />} />
          <Route path="/portfolio/:id/add" element={<CreateApartmentPage />} />
          <Route path="/apartment/:id" element={<ViewApartmentPage />} />
          <Route path="/apartment/:id/edit" element={<EditApartmentPage />} />
        </Routes>
      </Layout>
    </Router>
  );
};

export default App;
