-- 1. Superficie Total de Parques por Departamento
DELIMITER //
CREATE FUNCTION SuperficieTotalDepartamento(id_dep INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total_superficie DECIMAL(10,2);
    SELECT SUM(a.extension) INTO total_superficie
    FROM Area a
    JOIN Parque p ON a.id_parque = p.id_parque
    JOIN Parque_Departamento pd ON p.id_parque = pd.id_parque
    WHERE pd.id_departamento = id_dep;
    RETURN COALESCE(total_superficie, 0);
END //
DELIMITER ;
SELECT SuperficieTotalDepartamento(1);

-- 2. Total de especies en un área
DELIMITER //
CREATE FUNCTION TotalEspeciesPorArea(id_area INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT SUM(ea.num_inventario) INTO total
    FROM Especie_Area ea
    WHERE ea.id_area = id_area;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalEspeciesPorArea(5);

-- 3. Total de especies por tipo en un área
DELIMITER //
CREATE FUNCTION TotalEspeciesPorTipo(id_area INT, tipo_especie ENUM('Vegetal', 'Animal', 'Mineral')) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT SUM(ea.num_inventario) INTO total
    FROM Especie_Area ea
    JOIN Especie e ON ea.id_especie = e.id_especie
    WHERE ea.id_area = id_area AND e.tipo = tipo_especie;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalEspeciesPorTipo(5, 'Animal');

-- 4. Obtener el Presupuesto de un Proyecto
DELIMITER //
CREATE FUNCTION ObtenerPresupuestoProyecto(id_proyecto INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE presupuesto DECIMAL(10,2);
    SELECT presupuesto INTO presupuesto 
    FROM Proyecto_Investigacion 
    WHERE id_proyecto = id_proyecto;
    RETURN COALESCE(presupuesto, 0);
END //
DELIMITER ;
SELECT ObtenerPresupuestoProyecto(2);

-- 5. Costo Total en Sueldos del Personal Asignado a un Proyecto
DELIMITER //
CREATE FUNCTION CostoPersonalProyecto(id_proyecto INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE costo_total DECIMAL(10,2);
    SELECT SUM(pe.sueldo) INTO costo_total
    FROM Personal pe
    JOIN Proyecto_Investigador pi ON pe.id_personal = pi.id_personal
    WHERE pi.id_proyecto = id_proyecto;
    RETURN COALESCE(costo_total, 0);
END //
DELIMITER ;
SELECT CostoPersonalProyecto(3);

-- 6. Presupuesto total de un proyecto
DELIMITER //
CREATE FUNCTION ObtenerPresupuestoProyecto(id_proyecto INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE presupuesto DECIMAL(10,2);
    SELECT presupuesto INTO presupuesto FROM Proyecto_Investigacion
    WHERE id_proyecto = id_proyecto;
    RETURN COALESCE(presupuesto, 0);
END //
DELIMITER ;
SELECT ObtenerPresupuestoProyecto(2);

-- 7. Costo total en sueldos del personal en un proyecto
DELIMITER //
CREATE FUNCTION CostoPersonalProyecto(id_proyecto INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE costo_total DECIMAL(10,2);
    SELECT SUM(pe.sueldo) INTO costo_total
    FROM Personal pe
    JOIN Proyecto_Investigador pi ON pe.id_personal = pi.id_personal
    WHERE pi.id_proyecto = id_proyecto;
    RETURN COALESCE(costo_total, 0);
END //
DELIMITER ;
SELECT CostoPersonalProyecto(3);

-- 8. Duración de un Proyecto en Días
DELIMITER //
CREATE FUNCTION DuracionProyecto(id_proyecto INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE duracion INT;
    SELECT DATEDIFF(fecha_fin, fecha_inicio) INTO duracion
    FROM Proyecto_Investigacion WHERE id_proyecto = id_proyecto;
    RETURN COALESCE(duracion, 0);
END //
DELIMITER ;
SELECT DuracionProyecto(4);

-- 9. Promedio de Presupuesto de los Proyectos
DELIMITER //
CREATE FUNCTION PromedioPresupuestoProyectos() RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE promedio DECIMAL(10,2);
    SELECT AVG(presupuesto) INTO promedio FROM Proyecto_Investigacion;
    RETURN COALESCE(promedio, 0);
END //
DELIMITER ;
SELECT PromedioPresupuestoProyectos();

-- 10. Número de Vehículos Asignados a Vigilancia
DELIMITER //
CREATE FUNCTION TotalVehiculosVigilancia() RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total
    FROM Vehiculo v
    JOIN Personal p ON v.id_vehiculo = p.id_personal
    WHERE p.rol = 'Vigilancia';
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalVehiculosVigilancia();

-- 11. Total de empleados por rol
DELIMITER //
CREATE FUNCTION TotalPersonalPorRol(rol_personal ENUM('Gestion', 'Vigilancia', 'Conservacion', 'Investigacion')) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM Personal WHERE rol = rol_personal;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalPersonalPorRol('Vigilancia');

-- 12. Gasto total en sueldos de empleados
DELIMITER //
CREATE FUNCTION TotalGastoSueldos() RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    SELECT SUM(sueldo) INTO total FROM Personal;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalGastoSueldos();

-- 16. Total de visitantes en un parque
DELIMITER //
CREATE FUNCTION TotalVisitantesParque(id_parque INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(DISTINCT va.id_visitante) INTO total
    FROM Visitante_Alojamiento va
    JOIN Alojamiento a ON va.id_alojamiento = a.id_alojamiento
    WHERE a.id_parque = id_parque;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalVisitantesParque(3);

-- 17. Total de alojamientos ocupados en un parque
DELIMITER //
CREATE FUNCTION TotalAlojamientosOcupados(id_parque INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total
    FROM Alojamiento
    WHERE id_parque = id_parque AND fecha_salida > NOW();
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalAlojamientosOcupados(2);

-- 18. Promedio de Ocupación de Alojamientos en un Parque
DELIMITER //
CREATE FUNCTION PromedioOcupacionAlojamientos(id_parque INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE promedio DECIMAL(10,2);
    SELECT AVG(capacidad) INTO promedio FROM Alojamiento WHERE id_parque = id_parque;
    RETURN COALESCE(promedio, 0);
END //
DELIMITER ;
SELECT PromedioOcupacionAlojamientos(4);

-- 19. Total de visitantes en un alojamiento
DELIMITER //
CREATE FUNCTION TotalVisitantesAlojamiento(id_alojamiento INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total
    FROM Visitante_Alojamiento
    WHERE id_alojamiento = id_alojamiento;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalVisitantesAlojamiento(5);

-- 20. Total de alojamientos ocupados por un visitante
DELIMITER //
CREATE FUNCTION TotalAlojamientosVisitante(id_visitante INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total
    FROM Visitante_Alojamiento
    WHERE id_visitante = id_visitante;
    RETURN COALESCE(total, 0);
END //
DELIMITER ;
SELECT TotalAlojamientosVisitante(3);
