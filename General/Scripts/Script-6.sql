use prueba;

-- Ejercicio # 1 Muestre todos los empleados con sus vehiculos.
select E.e_id, E.Apellidos, E.Nombre, E.vhc_id
from Empleado E
left join Vehiculo V on E.vhc_id = V.vhc_id;

-- Ejercicio # 2 Muestre todos los empleados que no tengan ningun vehiculo asociado.

select E.e_id, E.Apellidos, E.Nombre, E.vhc_id
from Empleado E
left join Vehiculo V on E.vhc_id = V.vhc_id
where v.vhc_id is null;

 
-- Simulacion de FULL JOIN con LEFT JOIN y RIGHT JOIN combinados con UNION

select E.*, V.*


-- CONSULTAS ANIDADAS (SUBCONSULTAS)
select column_name (s)
from table1
where column_name OPERATOR (select column_name (s)
							from table2);

-- Mostrar un lista de paises cuya poblacion es mayor que el promedio de poblacion de 
-- todos los paises en la base de datos.
use world;
select Name, Population
from country 
where population > (select AVG(population) from country);

select AVG(population) as promedio from country; -- Promedio

-- Para crear un conjunto de resultados temporal que luego puede ser tratado como una
-- tabla en la consulta principal.

-- Ejemplo: Esta consulta calculara el promedio de poblacion de todas las ciudades que 
-- pertenecen al pais de Venezuela (codigo: "VEN")

select avg (population) as promedio
from (select population
		from city
		where countrycode = "VEN") as CiudadesVenezuela;

-- Consultas Anidadas (Subconsultas) - Subconsulta en la clausula SELECT
-- Ejemplo: Proporcionar una lista de paises de America junto con el numero total de ciudades que tiene cada uno.
-- El listado debe estar ordenado por el numero de ciudades del pais de forma deciente.

select P.name as pais, 
		(select count(*)
			from city C
			where C.countrycode = P.code) as NumCiudades
from country P
where P.Region = "North America" or P.Region = "South America"
order by NumCiudades desc;

-- 2.4. Consultas Anidadas (Subconsultas) - Subconsulta Correlacionada
-- Ejemplo: Mostrar un listado con las ciudades de toda America cuya poblacion es mayor que el
-- promedio de la ciudades en su mismo pais.
use world;

select C.name as Ciudad, C.Population as Poblacion, P.name as Pais
from city C 
join country P on P.code = C.CountryCode
where (P.Region = "North America" or P.Region = "South America") and
		c.Population > (select avg(C2.population)
							from city C2
								where C2.CountryCode = C.CountryCode);

-- 3. INDICES
-- Estructuras Estructuras que permiten encontrar y recorrer registros de forma mas optima.

							