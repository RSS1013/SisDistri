import React, { useState, useEffect } from 'react';
import { useAlert } from '../context/AlertContext';
import { Search, FileWarning, Database, Globe } from 'lucide-react';

const API_BASE_URL = 'http://localhost:5000';
const POKEAPI_URL = 'https://pokeapi.co/api/v2/pokemon?limit=50';

interface Pokemon {
  name: string;
  url?: string;
}

interface ResultData {
  status?: string;
  type?: string;
  message?: string;
  details?: string;
  data?: any;
}

const TestingPage: React.FC = () => {
  const [pokemonList, setPokemonList] = useState<Pokemon[]>([]);
  const [selectedPokemon, setSelectedPokemon] = useState('');
  const [loading, setLoading] = useState<boolean>(true);
  const [resultLoading, setResultLoading] = useState<boolean>(false);
  const [result, setResult] = useState<ResultData | null>(null);
  const [resultType, setResultType] = useState<'success' | 'error' | 'loading'>('loading');
  
  const { addAlert } = useAlert();
  
  useEffect(() => {
    loadPokemonList();
  }, []);
  
  const loadPokemonList = async () => {
    try {
      // Try with mock API first
      const response = await fetch(`${API_BASE_URL}/pokemon-list`).catch(() => null);
      
      if (response && response.ok) {
        const data = await response.json();
        if (data && data.length > 0) {
          setPokemonList(data.map((name: string) => ({ name })));
          setLoading(false);
          return;
        }
      }
      
      // Fallback to PokeAPI
      const pokeResponse = await fetch(POKEAPI_URL);
      const data = await pokeResponse.json();
      setPokemonList(data.results);
      setLoading(false);
    } catch (error) {
      console.error('Error loading Pokemon:', error);
      // Fallback list
      setPokemonList([
        { name: 'pikachu' },
        { name: 'charizard' },
        { name: 'bulbasaur' },
        { name: 'squirtle' }
      ]);
      setLoading(false);
      addAlert('warning', 'Usando lista de respaldo debido a un error de conexión');
    }
  };
  
  const searchPokemon = async () => {
    if (!selectedPokemon) return;
    
    setResultLoading(true);
    setResult(null);
    setResultType('loading');
    
    try {
      const response = await fetch(`${API_BASE_URL}/pokemon/${selectedPokemon}`);
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(JSON.stringify(errorData));
      }
      
      const data = await response.json();
      setResult(data);
      setResultType('success');
      addAlert('success', 'Búsqueda completada con éxito');
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : String(error);
      
      try {
        const parsedError = JSON.parse(errorMessage);
        setResult({
          type: parsedError.type || 'ERROR',
          message: parsedError.message || 'Error desconocido',
          details: parsedError.details || ''
        });
      } catch (e) {
        setResult({
          type: 'ERROR',
          message: errorMessage
        });
      }
      
      setResultType('error');
      addAlert('error', 'Error en la búsqueda');
    } finally {
      setResultLoading(false);
    }
  };
  
  const triggerException = async (type: string) => {
    setResultLoading(true);
    setResult(null);
    setResultType('loading');
    
    try {
      const response = await fetch(`${API_BASE_URL}/exceptions/${type}`);
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(JSON.stringify(errorData));
      }
      
      const data = await response.json();
      setResult(data);
      setResultType('success');
      addAlert('info', 'Excepción simulada correctamente');
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : String(error);
      
      try {
        const parsedError = JSON.parse(errorMessage);
        setResult({
          type: parsedError.type || 'ERROR',
          message: parsedError.message || 'Error desconocido',
          details: parsedError.details || ''
        });
      } catch (e) {
        setResult({
          type: 'ERROR',
          message: errorMessage
        });
      }
      
      setResultType('error');
      addAlert('warning', 'Excepción simulada capturada');
    } finally {
      setResultLoading(false);
    }
  };
  
  const getResultDisplay = () => {
    if (resultLoading) {
      return (
        <div className="result-area result-loading flex items-center justify-center">
          <span className="spinner mr-2"></span>
          <span>Procesando solicitud...</span>
        </div>
      );
    }
    
    if (!result) {
      return (
        <div className="result-area bg-slate-50 dark:bg-slate-800/50 text-foreground/60 flex items-center justify-center">
          Selecciona una acción para ver los resultados aquí
        </div>
      );
    }
    
    if (resultType === 'error') {
      return (
        <div className="result-area result-error">
          <strong>Error Type:</strong> {result.type || 'UNKNOWN'}{'\n\n'}
          <strong>Message:</strong> {result.message || 'Unknown error'}{'\n\n'}
          {result.details && (
            <>
              <strong>Details:</strong>{'\n'}
              {result.details}
            </>
          )}
        </div>
      );
    }
    
    return (
      <div className="result-area result-success">
        <strong>Status:</strong> {result.status || 'success'}{'\n\n'}
        <strong>Data:</strong>{'\n'}
        {JSON.stringify(result.data || result, null, 2)}
      </div>
    );
  };
  
  return (
    <div className="space-y-8">
      <div className="text-center mb-8">
        <h1 className="text-3xl font-bold mb-2">Pruebas de Excepciones</h1>
        <p className="text-foreground/70">
          Interfaz para probar excepciones y consultas a APIs externas
        </p>
      </div>
      
      <div className="card p-6 animate-fade-in">
        <h2 className="text-xl font-semibold mb-4 flex items-center">
          <Search className="mr-2 h-5 w-5 text-primary" />
          Buscador de Pokémon
        </h2>
        
        <div className="mb-4">
          {loading ? (
            <div className="flex items-center text-foreground/70">
              <span className="spinner mr-2"></span>
              Cargando lista de Pokémon...
            </div>
          ) : (
            <div className="flex flex-col md:flex-row gap-4">
              <select
                className="pokemon-select flex-1"
                value={selectedPokemon}
                onChange={(e) => setSelectedPokemon(e.target.value)}
              >
                <option value="">Selecciona un Pokémon</option>
                {pokemonList.map((pokemon) => (
                  <option key={pokemon.name} value={pokemon.name}>
                    {pokemon.name}
                  </option>
                ))}
              </select>
              
              <button
                className="btn btn-primary whitespace-nowrap"
                onClick={searchPokemon}
                disabled={!selectedPokemon}
              >
                <Search className="h-4 w-4 mr-2" />
                Buscar
              </button>
            </div>
          )}
        </div>
      </div>
      
      <div className="card p-6 animate-fade-in" style={{ animationDelay: '100ms' }}>
        <h2 className="text-xl font-semibold mb-4">Simulador de Excepciones</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <button
            className="exception-btn flex items-center justify-center"
            onClick={() => triggerException('file')}
          >
            <FileWarning className="mr-2 h-5 w-5" />
            Error de Archivo
          </button>
          
          <button
            className="exception-btn flex items-center justify-center"
            onClick={() => triggerException('database')}
          >
            <Database className="mr-2 h-5 w-5" />
            Error de Base de Datos
          </button>
          
          <button
            className="exception-btn flex items-center justify-center"
            onClick={() => triggerException('api')}
          >
            <Globe className="mr-2 h-5 w-5" />
            Error de API Externa
          </button>
        </div>
        
        <div className="mt-2 text-xs text-foreground/60">
          <p>Cada botón simula un tipo diferente de excepción en el servidor</p>
        </div>
      </div>
      
      <div className="card p-6 animate-fade-in" style={{ animationDelay: '200ms' }}>
        <h3 className="text-lg font-semibold mb-4">Resultado:</h3>
        {getResultDisplay()}
      </div>
    </div>
  );
};

export default TestingPage;