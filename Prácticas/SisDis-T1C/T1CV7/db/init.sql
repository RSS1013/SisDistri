CREATE DATABASE IF NOT EXISTS sistema_distribuido;
USE sistema_distribuido;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id INT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Inserción de roles
INSERT INTO roles (nombre) VALUES ('ADMIN'), ('EDITOR'), ('USER');

-- Usuario inicial: admin / admin123
INSERT INTO usuarios (username, password)
VALUES ('admin', '$2a$12$teuJUSqVRi7yANw5eTzNyuDrW1KMJXK.KkI9xbKerFNrwN3qTGBc2');

-- Asignación del rol ADMIN al usuario 'admin'
INSERT INTO usuario_roles (usuario_id, rol_id)
VALUES (1, 1);
