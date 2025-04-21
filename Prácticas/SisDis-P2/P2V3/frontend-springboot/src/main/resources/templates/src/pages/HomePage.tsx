import React from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, Database, Server, Code } from 'lucide-react';

const HomePage: React.FC = () => {
  return (
    <div className="space-y-12">
      <section className="text-center space-y-4 py-6 md:py-12">
        <h1 className="text-4xl md:text-5xl font-bold tracking-tight bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent animate-fade-in">
          Sistema Distribuido de Excepciones
        </h1>
        <p className="max-w-2xl mx-auto text-lg text-foreground/80 animate-fade-in" style={{ animationDelay: '100ms' }}>
          Una plataforma moderna para probar excepciones y API interactions en sistemas distribuidos.
        </p>
        <div className="flex flex-wrap justify-center gap-4 pt-4 animate-fade-in" style={{ animationDelay: '200ms' }}>
          <Link to="/testing" className="btn btn-primary btn-lg">
            Ir a Testing
            <ArrowRight className="ml-2 h-5 w-5" />
          </Link>
          <Link to="/login" className="btn btn-outline btn-lg">
            Iniciar Sesión
          </Link>
        </div>
      </section>
      
      <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card p-6 hover:shadow-md transition-all hover:-translate-y-1">
          <Database className="h-12 w-12 text-primary mb-4" />
          <h3 className="text-xl font-semibold mb-2">Simulación de Errores de Base de Datos</h3>
          <p className="text-foreground/80 mb-4">
            Simula y captura errores de conexión, consultas y transacciones en bases de datos.
          </p>
          <Link to="/testing" className="btn btn-ghost text-primary">
            Probar ahora
            <ArrowRight className="ml-1 h-4 w-4" />
          </Link>
        </div>
        
        <div className="card p-6 hover:shadow-md transition-all hover:-translate-y-1">
          <Server className="h-12 w-12 text-secondary mb-4" />
          <h3 className="text-xl font-semibold mb-2">Pruebas de API Externas</h3>
          <p className="text-foreground/80 mb-4">
            Interactúa con APIs externas como PokeAPI y maneja respuestas y errores de forma elegante.
          </p>
          <Link to="/testing" className="btn btn-ghost text-secondary">
            Explorar funciones
            <ArrowRight className="ml-1 h-4 w-4" />
          </Link>
        </div>
        
        <div className="card p-6 hover:shadow-md transition-all hover:-translate-y-1">
          <Code className="h-12 w-12 text-accent mb-4" />
          <h3 className="text-xl font-semibold mb-2">Manejo de Excepciones Personalizado</h3>
          <p className="text-foreground/80 mb-4">
            Simula errores de archivos y observa cómo se manejan las excepciones en el sistema.
          </p>
          <Link to="/testing" className="btn btn-ghost text-accent">
            Ver ejemplos
            <ArrowRight className="ml-1 h-4 w-4" />
          </Link>
        </div>
      </section>
      
      <section className="card p-8 bg-gradient-to-r from-primary/5 to-secondary/5 border-none">
        <div className="max-w-3xl mx-auto text-center">
          <h2 className="text-3xl font-bold mb-6">¿Por qué usar nuestro sistema?</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 text-left">
            <div className="flex items-start">
              <div className="rounded-full bg-success/10 p-2 mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-success">
                  <path d="M20 6 9 17l-5-5"></path>
                </svg>
              </div>
              <div>
                <h3 className="font-semibold mb-1">Pruebas Simuladas</h3>
                <p className="text-sm text-foreground/80">Simula errores sin riesgos reales en tu ambiente de producción.</p>
              </div>
            </div>
            <div className="flex items-start">
              <div className="rounded-full bg-primary/10 p-2 mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-primary">
                  <path d="M20 6 9 17l-5-5"></path>
                </svg>
              </div>
              <div>
                <h3 className="font-semibold mb-1">Interfaz Intuitiva</h3>
                <p className="text-sm text-foreground/80">Diseño moderno y fácil de usar para pruebas eficientes.</p>
              </div>
            </div>
            <div className="flex items-start">
              <div className="rounded-full bg-secondary/10 p-2 mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-secondary">
                  <path d="M20 6 9 17l-5-5"></path>
                </svg>
              </div>
              <div>
                <h3 className="font-semibold mb-1">Resultados Detallados</h3>
                <p className="text-sm text-foreground/80">Visualiza errores y respuestas con formato claro y legible.</p>
              </div>
            </div>
            <div className="flex items-start">
              <div className="rounded-full bg-accent/10 p-2 mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-accent">
                  <path d="M20 6 9 17l-5-5"></path>
                </svg>
              </div>
              <div>
                <h3 className="font-semibold mb-1">Integración API</h3>
                <p className="text-sm text-foreground/80">Prueba tus integraciones con APIs externas fácilmente.</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;