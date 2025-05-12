CREATE DATABASE IF NOT EXISTS sistema_distribuido;
USE sistema_distribuido;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE, 
    password VARCHAR(60) NOT NULL
);

DELETE FROM usuarios WHERE username = 'admin';

#INSERT INTO usuarios (username, password) VALUES ('RSadmin', 'RSadmin123');
