from flask import Flask, jsonify, request
import mysql.connector
import requests  # Para APIs de terceros (Pokémon)
import os

app = Flask(__name__)

# Configuración de la base de datos
db_config = {
    'host': 'mysql',  # Nombre del servicio en docker-compose
    'user': 'root',
    'password': 'root',
    'database': 'sistema_distribuido'
}

@app.route('/pokemon/<name>', methods=['GET'])
def get_pokemon(name):
    try:
        response = requests.get(f'https://pokeapi.co/api/v2/pokemon/{name}')
        response.raise_for_status()  # Lanza excepción si hay error HTTP
        return jsonify(response.json())
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 400

@app.route('/login', methods=['POST'])
def login():
    data = request.json
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM usuarios WHERE username = %s AND password = %s", 
                       (data['username'], data['password']))
        user = cursor.fetchone()
        if user:
            return jsonify({"message": "Login exitoso"})
        else:
            return jsonify({"error": "Credenciales inválidas"}), 401
    except mysql.connector.Error as e:
        return jsonify({"error": f"Error de base de datos: {e}"}), 500
    finally:
        if 'conn' in locals() and conn.is_connected():
            cursor.close()
            conn.close()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)