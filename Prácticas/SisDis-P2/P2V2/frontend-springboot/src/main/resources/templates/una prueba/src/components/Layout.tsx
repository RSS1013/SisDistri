import React from 'react';
import { Header } from './Header';
import { Footer } from './Footer';
import { useTheme } from '../context/ThemeContext';
import { AlertContainer } from './AlertContainer';

interface LayoutProps {
  children: React.ReactNode;
}

export const Layout: React.FC<LayoutProps> = ({ children }) => {
  const { theme } = useTheme();
  
  return (
    <div className={theme}>
      <div className="min-h-screen flex flex-col">
        <Header />
        <main className="flex-grow container py-8">
          {children}
        </main>
        <AlertContainer />
        <Footer />
      </div>
    </div>
  );
};