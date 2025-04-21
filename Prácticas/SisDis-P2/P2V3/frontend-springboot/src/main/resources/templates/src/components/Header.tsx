import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Menu, X, Sun, Moon } from 'lucide-react';
import { useTheme } from '../context/ThemeContext';

export const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { theme, toggleTheme } = useTheme();
  const location = useLocation();
  
  const toggleMenu = () => setIsMenuOpen(!isMenuOpen);
  
  const navigation = [
    { name: 'Home', href: '/' },
    { name: 'Login', href: '/login' },
    { name: 'Testing', href: '/testing' }
  ];
  
  return (
    <header className="bg-background sticky top-0 z-50 border-b border-border backdrop-blur supports-backdrop-blur:bg-background/60">
      <nav className="container mx-auto px-4 sm:px-6 lg:px-8" aria-label="Main navigation">
        <div className="flex h-16 items-center justify-between">
          <div className="flex items-center">
            <Link to="/" className="flex items-center text-primary font-bold text-xl hover:text-primary/80 transition-colors">
              <span className="sr-only">Exception Tester</span>
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="mr-2">
                <path d="M12 20h9"></path>
                <path d="M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4Z"></path>
              </svg>
              ExceptTester
            </Link>
          </div>
          
          {/* Desktop navigation */}
          <div className="hidden md:block">
            <div className="ml-10 flex items-center space-x-4">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  to={item.href}
                  className={`px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                    location.pathname === item.href
                      ? 'bg-primary/10 text-primary'
                      : 'text-foreground/80 hover:text-primary hover:bg-primary/5'
                  }`}
                  aria-current={location.pathname === item.href ? 'page' : undefined}
                >
                  {item.name}
                </Link>
              ))}
            </div>
          </div>
          
          <div className="flex items-center">
            <button
              type="button"
              className="btn btn-ghost p-2 rounded-md"
              onClick={toggleTheme}
              aria-label={theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode'}
            >
              {theme === 'dark' ? (
                <Sun className="h-5 w-5" />
              ) : (
                <Moon className="h-5 w-5" />
              )}
            </button>
            
            {/* Mobile menu button */}
            <button
              type="button"
              className="md:hidden btn btn-ghost ml-2 p-2 rounded-md"
              onClick={toggleMenu}
              aria-controls="mobile-menu"
              aria-expanded={isMenuOpen}
            >
              <span className="sr-only">Open main menu</span>
              {isMenuOpen ? (
                <X className="h-6 w-6" aria-hidden="true" />
              ) : (
                <Menu className="h-6 w-6" aria-hidden="true" />
              )}
            </button>
          </div>
        </div>
        
        {/* Mobile navigation */}
        <div
          className={`md:hidden ${isMenuOpen ? 'block animate-fade-in' : 'hidden'}`}
          id="mobile-menu"
        >
          <div className="pb-3 pt-2 space-y-1">
            {navigation.map((item) => (
              <Link
                key={item.name}
                to={item.href}
                className={`block px-4 py-2 text-base font-medium rounded-md ${
                  location.pathname === item.href
                    ? 'bg-primary/10 text-primary'
                    : 'text-foreground/80 hover:text-primary hover:bg-primary/5'
                }`}
                aria-current={location.pathname === item.href ? 'page' : undefined}
                onClick={() => setIsMenuOpen(false)}
              >
                {item.name}
              </Link>
            ))}
          </div>
        </div>
      </nav>
    </header>
  );
};