import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAlert } from '../context/AlertContext';
import { Eye, EyeOff, LogIn } from 'lucide-react';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const { addAlert } = useAlert();
  const navigate = useNavigate();
  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!username || !password) {
      addAlert('error', 'Por favor completa todos los campos');
      return;
    }
    
    setLoading(true);
    
    // Simulate login API call
    setTimeout(() => {
      if (username === 'admin' && password === 'password') {
        addAlert('success', '¡Login exitoso!');
        navigate('/testing');
      } else {
        addAlert('error', 'Credenciales incorrectas');
      }
      setLoading(false);
    }, 1500);
  };
  
  return (
    <div className="flex min-h-[calc(100vh-16rem)] items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="card w-full max-w-md p-8 animate-fade-in">
        <div className="text-center mb-6">
          <h2 className="text-2xl font-bold tracking-tight mb-2">Iniciar Sesión</h2>
          <p className="text-sm text-foreground/60">
            Ingresa tus credenciales para acceder al sistema
          </p>
        </div>
        
        <form className="space-y-6" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username" className="form-label">
              Usuario
            </label>
            <input
              id="username"
              name="username"
              type="text"
              autoComplete="username"
              required
              className="input"
              placeholder="Ingresa tu nombre de usuario"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="password" className="form-label">
              Contraseña
            </label>
            <div className="relative">
              <input
                id="password"
                name="password"
                type={showPassword ? 'text' : 'password'}
                autoComplete="current-password"
                required
                className="input pr-10"
                placeholder="Ingresa tu contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <button
                type="button"
                className="absolute inset-y-0 right-0 flex items-center pr-3 text-foreground/60 hover:text-foreground/80"
                onClick={() => setShowPassword(!showPassword)}
                tabIndex={-1}
              >
                {showPassword ? (
                  <EyeOff className="h-5 w-5" aria-hidden="true" />
                ) : (
                  <Eye className="h-5 w-5" aria-hidden="true" />
                )}
              </button>
            </div>
            <div className="flex items-center justify-end mt-2">
              <a href="#" className="text-sm text-primary hover:text-primary/80">
                ¿Olvidaste tu contraseña?
              </a>
            </div>
          </div>
          
          <div>
            <button
              type="submit"
              className="btn btn-primary w-full flex items-center justify-center"
              disabled={loading}
            >
              {loading ? (
                <>
                  <span className="spinner mr-2"></span>
                  Iniciando sesión...
                </>
              ) : (
                <>
                  <LogIn className="mr-2 h-5 w-5" />
                  Iniciar Sesión
                </>
              )}
            </button>
          </div>
          
          <div className="text-center">
            <span className="text-sm text-foreground/60">
              Demo: username = "admin", password = "password"
            </span>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;