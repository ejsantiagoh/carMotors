use world;
-- EVENTOS
-- Sintaxis
/*
 create event nombre_evento
 on schedule at <tiempo y periocidad>
 do
 	[Las instrucciones que se van a realizar en el evento]
*/

-- Eventos de una sola ejecucion
create table world_temp as
select *
from world.country;

create event eliminar_paises_antiguos
on schedule at current_timestamp + interval 1 day
do 
	delete from country where indepyear < 1900;
show events;

drop event eliminar_paises_antiguos;

select count(code) from country c where IndepYear < 1500;

create event eliminar_paises_antiguos
on schedule at current_timestamp + interval 1 minute
do 
	delete from world_temp where indepyear < 1500;

-- Eventos recurrentes
-- Ejemplo: aumentar la poblacion de los paises en 1.2% cada año 

create event actualizar_poblacion 
on schedule every 1 minute
do 
	update world_temp set population = population * 1.00000012; 

drop event actualizar_poblacion;
select population from world_temp where name = "Colombia";

-- 15:03 -> 46553106 : poblacion de Colombia

-- ACTUALIZAR EVENTO
alter event actualizar_poblacion
on schedule every 5 minute;
show events;

-- Habilitar el programador de eventos
set global event_calendar = on;

-- Ejercicio: crear un evento que elimine paises con menos de 1 millones de habitantes. El evento
-- se ejecuta 1 sola vez en el minuto siguiente
create event eliminar_paises_habitantes
on schedule at current_timestamp + interval 1 minute
do 
	delete from world_temp where population < 1000000;
show events;

-- Ejercicio: Programar un evento que aumente la poblacion de China en 1% cada 2 minutos
create event aumenta_poblacion
on schedule every 2 minute
do 
	update world_temp set population = population * 1.01;
select population, name from country where name = "China";
show events;

-- EVENTOS + PROCEDIMIENTOS ALMACENADOS
-- procedimiento que realice un backup de la tabla city;

Delimiter $$
create procedure backup_city()
begin 
	declare exit handler for sqlexception
	begin
		rollback;
	end;

	start transaction;
	
	create table if not exist city_backup as
	select * from city limit 1;
	
	truncate table city_backup;
	
	insert into city_backup select * from city;
	
	commit;
end $$

Delimiter ;

call  backup_city();
select count(id) from city;
select count(id) from city_backup;

create event respaldo_mensual
on schedule every 1 month
starts timestamp(current_date + interval (30 - day(current_date())) day '1:00:00')
do call backup_city();


create event respaldo_mensual
on schedule every 2 minute
starts timestamp(current_date + interval (30 - day(current_date())) day '1:00:00')
do call backup_city();

show events;

create table if not exists errores_eventos(
	id int auto_increment primary key,
	event_name varchar(100),
	texto_error text,
	time_error datetime default current_timestamp
);

Delimiter $$
create procedure backup_city_trace()
begin 
	declare exit handler for sqlexception
	declare mensaje_error text;
	begin
		get diagnostics condition 1 mensaje_error = message_text;
		insert into errores_eventos (event_name, texto_error, time_error)
				values("procedure backup_ciy_trace", mensaje_error);
		rollback;
	end;

	start transaction;
	
	create table if not exist city_backup as
	select * from city limit 1;
	
	truncate table city_backup;
	
	insert into city_backup select * from city;
	
	commit;
end $$

Delimiter ;
call backup_city_trace();
drop procedure backup_city_trace;

-- Ejercicio #1: Crea un evento que aumente la superficie
-- de los países en 5% cada año. (Cada 5 minutos para probar)

CREATE event aumentar_sup_paises
ON schedule AT current_time + INTERVAL 5 MINUTE
do
    update world_temp set surfaceArea = surfaceArea * 1.05;

SELECT Region, Name, Continent, SurfaceArea, Code  FROM world_temp
ORDER BY name;

SELECT Region, Name, Continent, SurfaceArea, Code  FROM world_temp WHERE name LIKE "Colombia";

-- Ejercicio #2: 
-- Crea un evento que registre en una tabla la cantidad de países por continente cada mes.

CREATE TABLE IF NOT EXISTS world_temp_summary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    continent VARCHAR(50),
    country_count INT,
    record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE EVENT registrar_paises_por_continente
ON SCHEDULE EVERY 1 MONTH
DO
    INSERT INTO world_temp_summary (continent, country_count)
    SELECT Continent, COUNT(*) FROM world_temp GROUP BY Continent;


-- Ejercicio #3:
-- Programa un evento que guarde un registro de cambios de población cada semana.

CREATE TABLE IF NOT EXISTS world_temp_population_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(3),
    old_population INT,
    new_population INT,
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE EVENT registrar_cambios_poblacion
ON SCHEDULE EVERY 1 WEEK
DO
    INSERT INTO world_temp_population_log (country_code, old_population, new_population)
    SELECT Code, Population, Population FROM world_temp;


-- Ejercicio #4:
-- Crea un evento que elimine países sin ciudades registradas cada 3 meses.
-- Debe dejar una traza en otra tabla.

CREATE TABLE IF NOT EXISTS world_temp_deleted_countries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(3),
    country_name VARCHAR(100),
    deletion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE EVENT eliminar_paises_sin_ciudades
ON SCHEDULE EVERY 3 MONTH
DO
    INSERT INTO world_temp_deleted_countries (country_code, country_name)
    SELECT w.Code, w.Name 
    FROM world_temp w
    LEFT JOIN city c ON w.Code = c.CountryCode
    WHERE c.ID IS NULL;
    
    DELETE w FROM world_temp w
    LEFT JOIN city c ON w.Code = c.CountryCode
    WHERE c.ID IS NULL;


-- Ejercicio #5:
-- Crear un evento que elimine y mueva a otra tabla todos los datos de los países
-- que se independizaron hace más de 500 años.

CREATE TABLE IF NOT EXISTS world_temp_old_independent_countries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(3),
    country_name VARCHAR(100),
    independence_year INT,
    move_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE EVENT mover_paises_independientes
ON SCHEDULE EVERY 1 YEAR
DO
    INSERT INTO world_temp_old_independent_countries (country_code, country_name, independence_year)
    SELECT Code, Name, IndepYear 
    FROM world_temp 
    WHERE IndepYear IS NOT NULL AND IndepYear <= YEAR(CURDATE()) - 500;
    
    DELETE FROM world_temp 
    WHERE IndepYear IS NOT NULL AND IndepYear <= YEAR(CURDATE()) - 500;


