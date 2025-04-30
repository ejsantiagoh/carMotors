use world;

-- ejemplo: funcion que pronostique el crecimiento de la poblacion de un pais en 10 años
-- se ha determinado que la poblacion crece con una tasa del 1.5% cada 10 años
drop function if exists world.pronostico_poblacion;

delimiter $$

create function pronostico_poblacion(nom_ciudad varchar(100))
returns decimal(10,3) 
deterministic
begin
    declare pob decimal(10,3); 
    select population into pob 
    from city 
    where lower(name) = lower(nom_ciudad); 
    return pob*1.015;
end $$

delimiter ;

SELECT pronostico_poblacion("Santafé de bogota");

-- mostrar un mensaje "Ciudad pequeña" y "Ciudad Grande" dependiendo
-- de la cantidad de habitantes.
-- Es pequeña si tiene menos de un millon
-- Es grande si tiene mas de un millon.


drop function if exists world.mensaje_Ciudad;

delimiter $$

create function mensaje_Ciudad(nom_ciudad varchar(100))
returns varchar(100)
deterministic
begin
	declare mensaje varchar(100);
	declare pob int;
	select population into pob 
	from city where lower(name) = lower(nom_ciudad);

if pob < 1000000 then
	set mensaje ="Ciudad pequeña";
else
	set mensaje ="Ciudad grande";
end if;
return mensaje;
end $$

delimiter ;

SELECT mensaje_Ciudad("Santafé de bogota")


-- ejemplo (while) sumar todas las poblaciones de la ciudad 
-- de un pais

drop function if Exists world.total_pob_pais;

delimiter $$
create function total_pob_pais(pais_nom varchar(100))
returns int
deterministic
begin
	declare total int default 0;
	declare sw int default 0;
	declare ciudad_id int;
	declare ciudad_pob int;
	
	declare cur cursor for
	SELECT c.id, c.population
	from city c
	join country p on c.countrycode = p.code 
	where lower(p.name) = lower(pais_nom); 
	
declare continue handler for not found set sw= 1; 

	open cur;
		 ciclo_while: while sw = 0 do
			fetch cur into ciudad_id, ciudad_pob;
			
			if sw = 1 then
				leave ciclo_while; 
			end if;
			
			set total = total + ciudad_pob;
		end while; 
	close cur;
	return	total;
end $$

delimiter ;


select total_pob_pais("Colombia")

-- Ejemplo: convertir el codigo anterior de while a LOOP



drop function if Exists world.total_pob_pais_loop;

delimiter $$
create function total_pob_pais_loop(pais_nom varchar(100))
returns int
deterministic
begin
	declare total int default 0;
	declare sw int default 0;
	declare ciudad_id int;
	declare ciudad_pob int;
	
	declare cur cursor for
	SELECT c.id, c.population
	from city c
	join country p on c.countrycode = p.code 
	where lower(p.name) = lower(pais_nom); 
	
declare continue handler for not found set sw= 1; 

	open cur;
		 ciclo_Loop: loop 
			fetch cur into ciudad_id, ciudad_pob;
			
			if sw then
				leave ciclo_loop; 
			end if;
			
			set total = total + ciudad_pob;
		end loop; 
	close cur;
	return	total;
end $$

delimiter ;

select total_pob_pais_loop("Colombia")

-- Ejemplo: escribir la funcion anterior que usa el while o loop
-- a repeat


drop function if Exists world.total_pob_pais_repat;

delimiter $$
create function total_pob_pais_repeat(pais_nom varchar(100))
returns int
deterministic
begin
	declare total int default 0;
	declare sw int default 0;
	declare ciudad_id int;
	declare ciudad_pob int;
	
	declare cur cursor for
	SELECT c.id, c.population
	from city c
	join country p on c.countrycode = p.code 
	where lower(p.name) = lower(pais_nom); 
	
declare continue handler for not found set sw= 1; 

	open cur;
		 repeat 
			fetch cur into ciudad_id, ciudad_pob;
			
			if not sw then
				set total = total + ciudad_pob;
			end if;
			
			
		until sw end repeat; 
	close cur;
	return	total;
end $$

delimiter ;

select total_pob_pais_repeat("Colombia")




-- Ejercicio: funcion convierte el nombre de una ciudad a mayúscula



drop function if Exists world.MaysName;

delimiter $$
create function MaysName (nameCity varchar(100))
returns varchar(100)
deterministic
begin
	declare cityname varchar(100);
select name into cityname
from city
where lower(name) = lower(nameCity);
return upper(cityname);
end $$

delimiter ;

select MaysName("paris");

-- Ejercicio: 
-- se quiere obtener los detalles de una ciudad como su nombre, poblacion y pais
-- en formato json construye una funcion que resuelva este requerimiento


Drop function if exists world.jsonData;

delimiter $$

create function jsonData(nameCity varchar(100))
returns varchar(500)
deterministic
begin
    declare idC int;
    declare nameC varchar(100);
    declare pobC INT;
    declare pais varchar(100);
    declare result varchar(500);
    
    declare cur cursor for
        select c.id, c.name, c.population, p.name
        from city c
        join country p on c.countrycode = p.code
        where lower(c.name) = lower(nameCity);

    open cur;

    fetch cur into idC, nameC, pobC, pais;
    set result = concat('{ "Id": ', idC, ', "Nombre": "', nameC, '", "Poblacion": ', pobC, ', "País": "', pais, '" }');
    close cur;
    return result;
end $$

delimiter ;


select jsonData("santafé de bogota");


-- Ejercicio:
-- hacer una funcion de Mysql que Sume hasta que el numero sea mayor que 100.
-- la funcion no recibe ningun parametro y en su logica arranca en 0 en incrementa 
-- un valor en 5

drop function if exists world.Sum100;

delimiter $$ 
create function Sum100( )
returns int
deterministic 
begin
	declare sumatoria int default 0;
	declare num int default 5;

		 ciclo_Loop: loop 
			if sumatoria > 100 then
				leave ciclo_loop; 
			end if;
			
			set sumatoria = sumatoria + num ;
		end loop;
		return sumatoria;
end$$


delimiter ;

select Sum100();

-- Ejercicio
-- hacer una funcion que encuentre si el nombre de un pais o ciudad es palindrome.
-- cuando lo haye, indique cual fue el nombre encontrado.
DROP FUNCTION IF EXISTS palindrome;

DELIMITER //

CREATE FUNCTION palindrome()
RETURNS VARCHAR(500)
DETERMINISTIC
BEGIN
    DECLARE pais VARCHAR(50);
    DECLARE ciudad VARCHAR(50);
    DECLARE mensaje VARCHAR(500) DEFAULT "";
    DECLARE sw INT DEFAULT 0;

    DECLARE cur CURSOR FOR
        SELECT c.name, p.name
        FROM city c
        JOIN country p ON c.countrycode = p.code
        WHERE REVERSE(c.name) = c.name OR REVERSE(p.name) = p.name;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET sw = 1;

    OPEN cur;

    ciclo_loop: LOOP
        FETCH cur INTO ciudad, pais;

        IF sw THEN
            LEAVE ciclo_loop;
        END IF;

        SET mensaje = CONCAT(mensaje, ciudad, " en ", pais, " es palíndromo. ");
    END LOOP;

    CLOSE cur;

    RETURN mensaje;
END //

DELIMITER ;

SELECT palindrome();




