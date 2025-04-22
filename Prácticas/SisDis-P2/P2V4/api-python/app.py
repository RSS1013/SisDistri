from flask import Flask, jsonify, request
from flask_cors import CORS
import mysql.connector
import requests
import os

app = Flask(__name__)
CORS(app)

# Configuración de la base de datos
db_config = {
    'host': 'mysql',
    'user': 'root',
    'password': 'root',
    'database': 'sistema_distribuido'
}

# Lista de Pokémon para el dropdown
POKEMONS = ['pikachu', 'charizard', 'bulbasaur', 'squirtle', 'mewtwo', 'ditto']

@app.route('/pokemon/<name>', methods=['GET'])
def get_pokemon(name):
    try:
        if name == 'simulate-error':
            # Simula error de API externa
            response = requests.get('https://pokeapi.co/api/v2/pokemon/invalid-pokemon')
            response.raise_for_status()
        
        response = requests.get(f'https://pokeapi.co/api/v2/pokemon/{name}')
        response.raise_for_status()
        return jsonify({
            'status': 'success',
            'data': response.json()
        })
    except requests.exceptions.RequestException as e:
        return jsonify({
            'status': 'error',
            'type': 'API_ERROR',
            'message': 'Error al consultar la API de Pokémon',
            'details': str(e)
        }), 400

@app.route('/exceptions/<exception_type>', methods=['GET'])
def trigger_exception(exception_type):
    try:
        if exception_type == 'file':
            # Simula error de archivo
            with open('nonexistent_file.txt') as f:
                data = f.read()
            return jsonify({'data': data})
        
        elif exception_type == 'database':
            # Simula error de base de datos
            conn = mysql.connector.connect(
                host='mysql',
                user='wrong_user',
                password='wrong_pass',
                database='nonexistent_db'
            )
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM nonexistent_table")
            return jsonify({'data': cursor.fetchall()})
        
        elif exception_type == 'api':
            # Error de API ya implementado en get_pokemon
            return get_pokemon('simulate-error')
            
        else:
            return jsonify({
                'status': 'error',
                'message': 'Tipo de excepción no válido'
            }), 400
            
    except Exception as e:
        return jsonify({
            'status': 'error',
            'type': exception_type.upper() + '_ERROR',
            'message': f'Error simulado: {exception_type}',
            'details': str(e)
        }), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)