use ambiental;

-- 1. Registrar una nueva entidad
DELIMITER //
CREATE PROCEDURE RegistrarEntidad(IN nombre VARCHAR(150))
BEGIN
    INSERT INTO Entidad (nombre) VALUES (nombre);
END //
DELIMITER ;

-- 2. Actualizar el nombre de una entidad
DELIMITER //
CREATE PROCEDURE ActualizarEntidad(IN id_entidad INT, IN nuevo_nombre VARCHAR(150))
BEGIN
    UPDATE Entidad SET nombre = nuevo_nombre WHERE id_entidad = id_entidad;
END //
DELIMITER ;

-- 3. Registrar un nuevo departamento
DELIMITER //
CREATE PROCEDURE RegistrarDepartamento(IN nombre VARCHAR(100), IN id_entidad INT)
BEGIN
    INSERT INTO Departamento (nombre, id_entidad) VALUES (nombre, id_entidad);
END //
DELIMITER ;

-- 4. Actualizar el nombre de un departamento
DELIMITER //
CREATE PROCEDURE ActualizarDepartamento(IN id_departamento INT, IN nuevo_nombre VARCHAR(100))
BEGIN
    UPDATE Departamento SET nombre = nuevo_nombre WHERE id_departamento = id_departamento;
END //
DELIMITER ;

-- 5. Registrar un nuevo parque
DELIMITER //
CREATE PROCEDURE RegistrarParque(IN nombre VARCHAR(150), IN fecha_declaracion DATE)
BEGIN
    INSERT INTO Parque (nombre, fecha_declaracion) VALUES (nombre, fecha_declaracion);
END //
DELIMITER ;

-- 6. Actualizar el nombre de un parque
DELIMITER //
CREATE PROCEDURE ActualizarParque(IN id_parque INT, IN nuevo_nombre VARCHAR(150))
BEGIN
    UPDATE Parque SET nombre = nuevo_nombre WHERE id_parque = id_parque;
END //
DELIMITER ;

-- 7. Registrar un área en un parque
DELIMITER //
CREATE PROCEDURE RegistrarArea(IN id_parque INT, IN nombre VARCHAR(150), IN extension DECIMAL(10,2))
BEGIN
    INSERT INTO Area (id_parque, nombre, extension) VALUES (id_parque, nombre, extension);
END //
DELIMITER ;

-- 8. Actualizar el nombre de un área
DELIMITER //
CREATE PROCEDURE ActualizarArea(IN id_area INT, IN nuevo_nombre VARCHAR(150))
BEGIN
    UPDATE Area SET nombre = nuevo_nombre WHERE id_area = id_area;
END //
DELIMITER ;

-- 9. Registrar una especie en el sistema
DELIMITER //
CREATE PROCEDURE RegistrarEspecie(IN nombre_cientifico VARCHAR(200), IN nombre_vulgar VARCHAR(150), IN tipo ENUM('Vegetal', 'Animal', 'Mineral'), IN inventario_individuos INT)
BEGIN
    INSERT INTO Especie (nombre_cientifico, nombre_vulgar, tipo, inventario_individuos) VALUES (nombre_cientifico, nombre_vulgar, tipo, inventario_individuos);
END //
DELIMITER ;

-- 10. Actualizar el inventario de una especie
DELIMITER //
CREATE PROCEDURE ActualizarInventarioEspecie(IN id_especie INT, IN nuevo_inventario INT)
BEGIN
    UPDATE Especie SET inventario_individuos = nuevo_inventario WHERE id_especie = id_especie;
END //
DELIMITER ;

-- 11. Registrar un visitante
DELIMITER //
CREATE PROCEDURE RegistrarVisitante(IN cedula VARCHAR(20), IN nombre VARCHAR(150), IN direccion VARCHAR(100), IN profesion VARCHAR(100))
BEGIN
    INSERT INTO Visitante (cedula, nombre, direccion, profesion) VALUES (cedula, nombre, direccion, profesion);
END //
DELIMITER ;

-- 12. Actualizar la dirección de un visitante
DELIMITER //
CREATE PROCEDURE ActualizarDireccionVisitante(IN id_visitante INT, IN nueva_direccion VARCHAR(100))
BEGIN
    UPDATE Visitante SET direccion = nueva_direccion WHERE id_visitante = id_visitante;
END //
DELIMITER ;

-- 13. Registrar un alojamiento
DELIMITER //
CREATE PROCEDURE RegistrarAlojamiento(IN id_parque INT, IN capacidad INT, IN categoria VARCHAR(50), IN fecha_ingreso DATE, IN fecha_salida DATE)
BEGIN
    INSERT INTO Alojamiento (id_parque, capacidad, categoria, fecha_ingreso, fecha_salida) VALUES (id_parque, capacidad, categoria, fecha_ingreso, fecha_salida);
END //
DELIMITER ;

-- 14. Actualizar la capacidad de un alojamiento
DELIMITER //
CREATE PROCEDURE ActualizarCapacidadAlojamiento(IN id_alojamiento INT, IN nueva_capacidad INT)
BEGIN
    UPDATE Alojamiento SET capacidad = nueva_capacidad WHERE id_alojamiento = id_alojamiento;
END //
DELIMITER ;

-- 15. Registrar personal en el sistema
DELIMITER //
CREATE PROCEDURE RegistrarPersonal(IN tipo_documento VARCHAR(100), IN documento INT, IN nombre VARCHAR(150), IN direccion VARCHAR(100), IN telefono VARCHAR(20), IN sueldo DECIMAL(10,2), IN rol ENUM('Gestion', 'Vigilancia', 'Conservacion', 'Investigacion'))
BEGIN
    INSERT INTO Personal (tipo_documento, documento, nombre, direccion, telefono, sueldo, rol) VALUES (tipo_documento, documento, nombre, direccion, telefono, sueldo, rol);
END //
DELIMITER ;

-- 16. Actualizar el sueldo de un empleado
DELIMITER //
CREATE PROCEDURE ActualizarSueldo(IN id_personal INT, IN nuevo_sueldo DECIMAL(10,2))
BEGIN
    UPDATE Personal SET sueldo = nuevo_sueldo WHERE id_personal = id_personal;
END //
DELIMITER ;

-- 17. Registrar un proyecto de investigación
DELIMITER //
CREATE PROCEDURE RegistrarProyecto(IN nombre VARCHAR(200), IN presupuesto DECIMAL(10,2), IN fecha_inicio DATE, IN fecha_fin DATE)
BEGIN
    INSERT INTO Proyecto_Investigacion (nombre, presupuesto, fecha_inicio, fecha_fin) VALUES (nombre, presupuesto, fecha_inicio, fecha_fin);
END //
DELIMITER ;

-- 18. Asignar un investigador a un proyecto
DELIMITER //
CREATE PROCEDURE AsignarInvestigadorProyecto(IN id_proyecto INT, IN id_personal INT)
BEGIN
    INSERT INTO Proyecto_Investigador (id_proyecto, id_personal) VALUES (id_proyecto, id_personal);
END //
DELIMITER ;

-- 19. Asignar una especie a un proyecto
DELIMITER //
CREATE PROCEDURE AsignarEspecieProyecto(IN id_proyecto INT, IN id_especie INT)
BEGIN
    INSERT INTO Proyecto_Especie (id_proyecto, id_especie) VALUES (id_proyecto, id_especie);
END //
DELIMITER ;

-- 20. Registrar un pago de alojamiento
DELIMITER //
CREATE PROCEDURE RegistrarPagoAlojamiento(IN id_alojamiento INT, IN pago_total DECIMAL(10,2), IN fecha_pago DATE)
BEGIN
    INSERT INTO Pagos (id_alojamiento, pago_total, fecha_pago) VALUES (id_alojamiento, pago_total, fecha_pago);
END //
DELIMITER ;

-- 1. Registrar una nueva entidad
CALL RegistrarEntidad('Nueva Entidad Ambiental');

-- 2. Actualizar el nombre de una entidad
CALL ActualizarEntidad(1, 'Entidad Ambiental Actualizada');

-- 3. Registrar un nuevo departamento
CALL RegistrarDepartamento('Nuevo Departamento', 1);

-- 4. Actualizar el nombre de un departamento
CALL ActualizarDepartamento(1, 'Departamento Actualizado');

-- 5. Registrar un nuevo parque
CALL RegistrarParque('Parque Nacional', '2025-03-09');

-- 6. Actualizar el nombre de un parque
CALL ActualizarParque(1, 'Parque Nacional Actualizado');

-- 7. Registrar un área en un parque
CALL RegistrarArea(1, 'Área de Conservación', 150.75);

-- 8. Actualizar el nombre de un área
CALL ActualizarArea(1, 'Área de Conservación Actualizada');

-- 9. Registrar una especie en el sistema
CALL RegistrarEspecie('Panthera onca', 'Jaguar', 'Animal', 50);

-- 10. Actualizar el inventario de una especie
CALL ActualizarInventarioEspecie(1, 60);

-- 11. Registrar un visitante
CALL RegistrarVisitante('1234567890', 'Juan Pérez', 'Calle 123', 'Biólogo');

-- 12. Actualizar la dirección de un visitante
CALL ActualizarDireccionVisitante(1, 'Avenida 456');

-- 13. Registrar un alojamiento
CALL RegistrarAlojamiento(1, 4, 'Cabaña', '2025-03-09', '2025-03-15');

-- 14. Actualizar la capacidad de un alojamiento
CALL ActualizarCapacidadAlojamiento(1, 6);

-- 15. Registrar personal en el sistema
CALL RegistrarPersonal('CC', 987654321, 'María López', 'Avenida 456', '555-1234', 3000.00, 'Vigilancia');

-- 16. Actualizar el sueldo de un empleado
CALL ActualizarSueldo(1, 3500.00);

-- 17. Registrar un proyecto de investigación
CALL RegistrarProyecto('Proyecto de Conservación de Jaguares', 50000.00, '2025-01-01', '2025-12-31');

-- 18. Asignar un investigador a un proyecto
CALL AsignarInvestigadorProyecto(1, 1);

-- 19. Asignar una especie a un proyecto
CALL AsignarEspecieProyecto(51, 50);

-- 20. Registrar un pago de alojamiento
CALL RegistrarPagoAlojamiento(1, 1500.00, '2025-03-09');