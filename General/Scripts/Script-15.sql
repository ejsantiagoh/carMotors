-- Crear la tabla Reportes
CREATE TABLE Reportes (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla Pagos
CREATE TABLE Pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_alojamiento INT NOT NULL,
    pago_total DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    FOREIGN KEY (id_alojamiento) REFERENCES Alojamiento(id_alojamiento)
);

-- Crear la tabla Log_Operaciones
CREATE TABLE Log_Operaciones (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla Log_Salarios
CREATE TABLE Log_Salarios (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_personal INT NOT NULL,
    sueldo_anterior DECIMAL(10,2) NOT NULL,
    sueldo_nuevo DECIMAL(10,2) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_personal) REFERENCES Personal(id_personal)
);

