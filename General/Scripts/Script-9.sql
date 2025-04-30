DROP TABLE IF EXISTS citytemp;
CREATE TABLE citytemp (
    id int PRIMARY KEY,
    name varchar(100),
    population int
);

DROP FUNCTION IF EXISTS InsertCityFunction;

DELIMITER $$

CREATE FUNCTION InsertCityFunction(
    id INT,
    cityName VARCHAR(255),
    population INT
)
RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
    DECLARE resultMessage VARCHAR(255)
    DEFAULT 'Ciudad insertada exitosamente.';

    -- Intentamos insertar una nueva ciudad
    BEGIN
        -- Usamos un bloque de manejo de excepciones
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
        SET resultMessage = 'Error: La ciudad ya existe o ocurrió otro error.';

        -- Intentamos insertar la ciudad
        INSERT INTO citytemp VALUES (id, cityName, population);
    END;

    -- Retornamos el mensaje
    RETURN resultMessage;
END $$

DELIMITER ;

SELECT InsertCityFunction(2, "Bucaramanga", 1000000) AS mensaje;

SELECT * FROM citytemp c;

-- Ejercicio: crear un usuario alimentador que solo se pueda conectar desde el localhost y que
-- solo tenga permisos de insercion en la tabla countrylanguage de bd world

create user alimentador@localhost identified by "Perro254!";
grant INSERT  on world.countrylanguage  to alimentador@localhost;
flush privileges;
show grants for alimentador@localhost;

-- crear un usuario administrador
create user "admin"@"localhost" identified by "admin123";
grant all privileges on *.* to "admin"@"localhost" with grant option;
flush privileges;
show grants for admin@localhost;

-- permisos a columnas

-- PERMISOS A COLUMNAS
/*
GRANT tipo_privilegio (columna1, columna2, ...) ON nombre_bd.nombre_tabla TO 'usuario'@'host';
*/

-- analista: permisos para ver campos name, population de tabla city desde cualquier parte
create user "analista"@"%" identified by "analista123";
grant select(name, population) on world.city to "analista"@"%";
flush privileges;
show grants for "analista"@"%";

-- borrar usuario
drop user "analista"@"%";
select user, host from mysql.user;

-- borrar permisos de un usuario
-- REVOKE tipo_privilegio[, tipo2_privilegio] on nivel_privilegio from usuario@host;

revoke select(population) on world.city from "analista"@"%";
flush privileges;
show grants for "analista"@"%";

-- LIMITAR CONEXION a la base de datos
grant select(name, population) on world.city to "analista"@"%" with max_queries_per_hour 600;

-- max_queries_per_hour
-- max_updates_per_hour
-- max_connections_per_hour
-- max_user_connections : el # maximo de conexiones simultaneas totales para un usuario.

-- CONSULTAS PREPARADAS 

-- SINTAXIS:
/*
 PREPARE nombre_sentencia FROM 'sentencia SQL con ? como marcadores de posicion';
 SET @variable1 = valor1;
 SET @variable2 = valor2;
 ...
 EXECUTE nombre_sentencia USING @variable1, @variable2, ...;
 DEALLOCATE PREPARE nombre_sentencia; -- Libera la sentencia preparada (buena practica).
*/

-- Consulta preparada para obtener informacion de una ciudad
prepare pmtcity from "select * from city where id = ?";
set @id_city = 10;
execute pmtcity using @id_city;
deallocate prepare pmtcity;

-- Insercion preparada
prepare instempcity from "insert into world.citytemp (id,name,population) values (?, ?, ?)";
set @id = 6;
set @nombre = "Mogotes";
set @population = 200000;
execute instempcity using @id, @nombre, @poblacion;
select * from citytemp c;

-- Ejercicio 1: Crea un usuario llamado 'modificador' que pueda insertar y actualizar datos en la tabla `city`,
-- pero no pueda eliminarlos. Ademas debe conectarse desde la IP '192.168.1.50'
-- Crear el usuario 'modificador' con acceso solo desde '192.168.1.50'
create user "modificador"@"192.168.1.50" identified by "tu_contraseña";
grant insert, update on city to "modificador"@"192.168.1.50" ;
flush privileges;



-- Ejercicio 2: Crea un procedimiento almacenado que reciba como parametro el codigo de un pais.
-- Dentro del procedimiento, crea un usuario temporal llamado 'temp_user' con una contraseña aleatoria.
-- Otorga al usuario 'temp_user' permiso para seleccionar (solo las columnas `Name` y `Population`) de la tabla `city`
-- para las ciudades que pertenezcan al pais recibido como parametro.
-- El procedimiento debe devolver el nombre de usuario y la contraseña generada.
-- Asegurate de que si ocurre algun error, se muestre un mensaje.

DELIMITER $$

CREATE PROCEDURE CreateTempUser(IN countryCode CHAR(3))
BEGIN
    DECLARE tempPassword VARCHAR(16);
    DECLARE errorOccurred INT DEFAULT 0;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET errorOccurred = 1;
        SELECT 'Error: No se pudo crear el usuario o asignar permisos.' AS Message;
    END;

    SET tempPassword = LEFT(UUID(), 16);

    SET @createUser = CONCAT('CREATE USER \'temp_user\'@\'%\' IDENTIFIED BY \'', tempPassword, '\';');
    PREPARE stmt FROM @createUser;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    SET @createView = CONCAT(
        'CREATE OR REPLACE VIEW world.CityView_', countryCode, ' AS ',
        'SELECT Name, Population FROM world.City WHERE CountryCode = \'', countryCode, '\';'
    );
    PREPARE stmt FROM @createView;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    SET @grantPermissions = CONCAT(
        'GRANT SELECT ON world.CityView_', countryCode, ' TO \'temp_user\'@\'%\';'
    );
    PREPARE stmt FROM @grantPermissions;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    FLUSH PRIVILEGES;

    IF errorOccurred = 0 THEN
        SELECT 'temp_user' AS Username, tempPassword AS Password;
    END IF;
    
    SET @dropUser = 'DROP USER \'temp_user\'@\'%\';';
    PREPARE stmt FROM @dropUser;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    
END $$

DELIMITER ;

CALL CreateTempUser('COL');


-- Ejercicio 3: Crea una funcion que devuelva el codigo de un pais al azar.
DELIMITER $$

CREATE FUNCTION RandomCountryCode() 
RETURNS VARCHAR(100) 
DETERMINISTIC
BEGIN
    DECLARE countryCode CHAR(3);


    SELECT Code INTO countryCode 
    FROM country 
    ORDER BY RAND() 
    LIMIT 1;

    RETURN countryCode;
END$$

DELIMITER ;

SELECT RandomCountryCode();


