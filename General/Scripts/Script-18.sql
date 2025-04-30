-- 1. Generar un reporte semanal de visitantes registrados
DELIMITER //
CREATE EVENT Reporte_Visitantes_Semanal
ON SCHEDULE EVERY 7 DAY STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Visitantes', CONCAT('Total de visitantes en la última semana: ', COUNT(*)), NOW()
    FROM Visitante WHERE fecha_registro >= DATE_SUB(NOW(), INTERVAL 7 DAY);
END;
//
DELIMITER ;

-- 2. Generar reporte semanal de alojamientos ocupados
DELIMITER //
CREATE EVENT Reporte_Alojamientos_Semanal
ON SCHEDULE EVERY 7 DAY STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Alojamientos', CONCAT('Total de alojamientos ocupados: ', COUNT(*)), NOW()
    FROM Alojamiento WHERE fecha_salida >= NOW();
END;
//
DELIMITER ;

-- 3. Generar reporte mensual de visitantes por parque
DELIMITER //
CREATE EVENT Reporte_Visitantes_Mensual
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Visitantes Mensual', CONCAT('Visitantes registrados en el mes actual: ', COUNT(*)), NOW()
    FROM Visitante WHERE MONTH(fecha_registro) = MONTH(NOW()) AND YEAR(fecha_registro) = YEAR(NOW());
END;
//
DELIMITER ;

-- 4. Generar reporte mensual de ingresos por alojamientos
DELIMITER //
CREATE EVENT Reporte_Ingresos_Alojamientos
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Ingresos Alojamiento', CONCAT('Ingresos del mes: ', SUM(pago_total)), NOW()
    FROM Pagos WHERE MONTH(fecha_pago) = MONTH(NOW()) AND YEAR(fecha_pago) = YEAR(NOW());
END;
//
DELIMITER ;

-- 5. Reporte diario de capacidad de alojamientos
DELIMITER //
CREATE EVENT Reporte_Capacidad_Alojamientos
ON SCHEDULE EVERY 1 DAY STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Capacidad Alojamiento', CONCAT('Alojamientos ocupados hoy: ', COUNT(*)), NOW()
    FROM Alojamiento WHERE fecha_ingreso = CURDATE();
END;
//
DELIMITER ;

-- 6. Actualizar inventario de especies cada 15 días
DELIMITER //
CREATE EVENT Actualizar_Inventario_Especies
ON SCHEDULE EVERY 15 DAY STARTS CURRENT_TIMESTAMP
DO
BEGIN
    UPDATE Especie
    SET inventario_individuos = (SELECT SUM(num_inventario) FROM Especie_Area WHERE Especie_Area.id_especie = Especie.id_especie);
END;
//
DELIMITER ;

-- 7. Registrar especies en peligro de extinción cada mes
DELIMITER //
CREATE EVENT Registrar_Especies_Peligro
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Especies en Peligro', CONCAT('Especie ', nombre_cientifico, ' con menos de 50 individuos'), NOW()
    FROM Especie WHERE inventario_individuos < 50;
END;
//
DELIMITER ;

-- 8. Notificar si una especie se ha extinguido
DELIMITER //
CREATE EVENT Notificar_Especies_Extintas
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Especie Extinta', CONCAT('La especie ', nombre_cientifico, ' ha desaparecido'), NOW()
    FROM Especie WHERE inventario_individuos = 0;
END;
//
DELIMITER ;

-- 9. Control de especies invasoras cada 3 meses
DELIMITER //
CREATE EVENT Control_Especies_Invasoras
ON SCHEDULE EVERY 3 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    UPDATE Especie
    SET estado = 'Invasora'
    WHERE nombre_cientifico LIKE '%exótica%' OR nombre_vulgar LIKE '%invasora%';
END;
//
DELIMITER ;

-- 10. Reporte de nuevas especies registradas cada mes
DELIMITER //
CREATE EVENT Reporte_Nuevas_Especies
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Nuevas Especies', CONCAT('Especies registradas este mes: ', COUNT(*)), NOW()
    FROM Especie WHERE fecha_registro >= DATE_SUB(NOW(), INTERVAL 1 MONTH);
END;
//
DELIMITER ;

-- 11. Aumentar sueldos un 5% cada año
DELIMITER //
CREATE EVENT Aumento_Sueldos_Anual
ON SCHEDULE EVERY 1 YEAR STARTS CURRENT_TIMESTAMP
DO
BEGIN
    UPDATE Personal SET sueldo = sueldo * 1.05;
END;
//
DELIMITER ;

-- 12. Reporte de empleados activos cada mes
DELIMITER //
CREATE EVENT Reporte_Empleados_Activos
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Empleados Activos', CONCAT('Total empleados activos: ', COUNT(*)), NOW()
    FROM Personal WHERE estado = 'Activo';
END;
//
DELIMITER ;

-- 13. Reporte de personal con cambios salariales
DELIMITER //
CREATE EVENT Reporte_Cambios_Salariales
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Cambios Salariales', CONCAT('Empleado ', id_personal, ' tuvo cambio de sueldo'), NOW()
    FROM Log_Salarios WHERE fecha_cambio >= DATE_SUB(NOW(), INTERVAL 1 MONTH);
END;
//
DELIMITER ;

-- 14. Control de empleados con contratos vencidos
DELIMITER //
CREATE EVENT Control_Contratos_Vencidos
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    UPDATE Personal SET estado = 'Inactivo' WHERE fecha_fin_contrato <= NOW();
END;
//
DELIMITER ;

-- 15. Registro de personal que se jubila
DELIMITER //
CREATE EVENT Registro_Jubilados
ON SCHEDULE EVERY 1 YEAR STARTS CURRENT_TIMESTAMP
DO
BEGIN
    INSERT INTO Reportes (tipo, descripcion, fecha)
    SELECT 'Personal Jubilado', CONCAT('Empleado ID ', id_personal, ' ha sido jubilado'), NOW()
    FROM Personal WHERE TIMESTAMPDIFF(YEAR, fecha_nacimiento, NOW()) >= 65;
END;
//
DELIMITER ;

-- 16. Limpieza de logs antiguos cada 6 meses
DELIMITER //
CREATE EVENT Limpiar_Logs_Antiguos
ON SCHEDULE EVERY 6 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    DELETE FROM Log_Operaciones WHERE fecha < DATE_SUB(NOW(), INTERVAL 1 YEAR);
END;
//
DELIMITER ;

-- 17. Limpieza de registros antiguos de visitantes cada semana
DELIMITER //
CREATE EVENT Limpiar_Visitantes_Antiguos
ON SCHEDULE EVERY 1 WEEK STARTS CURRENT_TIMESTAMP
DO
BEGIN
    DELETE FROM Visitante
    WHERE id_visitante NOT IN (
        SELECT id_visitante
        FROM Visitante_Alojamiento
        WHERE fecha_ingreso >= DATE_SUB(NOW(), INTERVAL 1 YEAR)
    );
END;
//
DELIMITER ;

-- 18. Mantenimiento de índices de la base de datos
DELIMITER //
CREATE EVENT Mantener_Indices
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    OPTIMIZE TABLE Personal, Especie, Alojamiento, Visitante;
END;
//
DELIMITER ;

-- 19. Revisión de permisos de usuario cada mes
DELIMITER //
CREATE EVENT Revisar_Permisos_Usuarios
ON SCHEDULE EVERY 1 MONTH STARTS CURRENT_TIMESTAMP
DO
BEGIN
    -- Validar roles y permisos aquí
END;
//
DELIMITER ;

-- 20. Registro de accesos a la base de datos
DELIMITER //
CREATE EVENT Registro_Accesos
ON SCHEDULE EVERY 1 DAY STARTS CURRENT_TIMESTAMP
DO
BEGIN
    -- Insertar datos de acceso en log
END;
//
DELIMITER ;
