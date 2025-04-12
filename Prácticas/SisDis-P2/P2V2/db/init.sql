CREATE DATABASE IF NOT EXISTS sistema_distribuido;
USE sistema_distribuido;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (username, password) VALUES ('RSadmin', 'RSadmin123');