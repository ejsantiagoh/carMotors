-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bootcamp_mvc;
USE bootcamp_mvc;

-- Crear la tabla estudiantes
CREATE TABLE IF NOT EXISTS estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    sexo ENUM('M', 'F') NOT NULL
);

-- Insertar 20 estudiantes de ejemplo
INSERT INTO estudiantes (nombre, sexo) VALUES 
('Juan Perez', 'M'),
('Ana Torres', 'F'),
('Carlos Mendoza', 'M'),
('Lucía García', 'F'),
('Pedro Ramírez', 'M'),
('María Sánchez', 'F'),
('José López', 'M'),
('Laura Díaz', 'F'),
('Andrés Gómez', 'M'),
('Patricia Herrera', 'F'),
('Santiago Vargas', 'M'),
('Verónica Cruz', 'F'),
('Daniel Morales', 'M'),
('Gabriela Castro', 'F'),
('Luis Torres', 'M'),
('Camila Peña', 'F'),
('Felipe Rojas', 'M'),
('Valentina Ruiz', 'F'),
('Emilio Silva', 'M'),
('Paula Ortega', 'F');