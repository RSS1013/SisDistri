import React, { useState } from 'react';
import { Layout } from './components/Layout';
import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import TestingPage from './pages/TestingPage';
import { AlertProvider } from './context/AlertContext';
import { ThemeProvider } from './context/ThemeContext';

function App() {
  return (
    <ThemeProvider>
      <AlertProvider>
        <Layout>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/testing" element={<TestingPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </Layout>
      </AlertProvider>
    </ThemeProvider>
  );
}

export default App;