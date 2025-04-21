import React from 'react';
import { Link } from 'react-router-dom';
import { Twitter, Github, Linkedin } from 'lucide-react';

export const Footer: React.FC = () => {
  return (
    <footer className="border-t border-border mt-auto">
      <div className="container py-8 md:py-12">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div>
            <h3 className="text-lg font-semibold mb-4">ExceptTester</h3>
            <p className="text-sm text-foreground/80 mb-4">
              A modern interface for testing exceptions and API interactions.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="text-foreground/70 hover:text-primary transition-colors">
                <span className="sr-only">Twitter</span>
                <Twitter size={20} />
              </a>
              <a href="#" className="text-foreground/70 hover:text-primary transition-colors">
                <span className="sr-only">GitHub</span>
                <Github size={20} />
              </a>
              <a href="#" className="text-foreground/70 hover:text-primary transition-colors">
                <span className="sr-only">LinkedIn</span>
                <Linkedin size={20} />
              </a>
            </div>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-4">Quick Links</h3>
            <ul className="space-y-2 text-sm">
              <li>
                <Link to="/" className="text-foreground/80 hover:text-primary transition-colors">
                  Home
                </Link>
              </li>
              <li>
                <Link to="/login" className="text-foreground/80 hover:text-primary transition-colors">
                  Login
                </Link>
              </li>
              <li>
                <Link to="/testing" className="text-foreground/80 hover:text-primary transition-colors">
                  Testing Interface
                </Link>
              </li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-4">Resources</h3>
            <ul className="space-y-2 text-sm">
              <li>
                <a href="https://pokeapi.co/" className="text-foreground/80 hover:text-primary transition-colors">
                  PokéAPI
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/80 hover:text-primary transition-colors">
                  Documentation
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/80 hover:text-primary transition-colors">
                  API Reference
                </a>
              </li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-4">Contact</h3>
            <ul className="space-y-2 text-sm">
              <li className="text-foreground/80">
                Email: info@excepttester.com
              </li>
              <li className="text-foreground/80">
                Support: support@excepttester.com
              </li>
            </ul>
          </div>
        </div>
        
        <div className="border-t border-border mt-8 pt-8 text-center">
          <p className="text-sm text-foreground/60">
            &copy; {new Date().getFullYear()} ExceptTester. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  );
};