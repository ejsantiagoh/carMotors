use world;

# Funciones de cadena de MySQL
-- CONCAT
select concat("Hola"," ","C4") as CONCATENACION; 

-- LENGTH
select length (concat("Hola"," ","C4")) as LONGITUD;

-- RIGTH
select right("MySQL", 3) as DERECHA;

-- LEFT
select left ("MySQL", 3) as IZQUIERDA;

-- SUBSTRING
select substr("MySQL", 2) as SUBCADENA, substr("MySQL", 2, 3) as SUBCADENA2; 

-- TRIM
select trim("   Hola C4   ") as SINESPACIOS;

-- RTRIM
select concat("[", rtrim("      Hola C4    "), "]") as SINESPACIOSDRECHA;

-- LTRIM
select concat("[", ltrim("      Hola C4    "), "]") as SINESPACIOSIZQ;

-- UPPER
select upper("campus lands") as ENMAYUSCULAS;

-- LOWER
select lower("Campus Lands") as ENMINUSCULA; 

-- REPLACE 
select replace("Hola Mundo", "Mundo", "C4") as REMPLANZANDO;

-- CONVERT
-- Convierte una cadena de cadena a otro tipo de dato
select convert ("Hola", binary) as CONVERSION;
select convert ("2005-02-12", date) CONV_FECHA;
select convert ("123.45", SIGNED) as CONV_NUM;

## LOCATE
-- Devuelve la posición de la primera ocurrencia de una subcadena en la cadena.
select locate("C4", "Hola C4") as POS_OCURRENCIA;

## REVERSE
-- Invierte una cadena de teexto
select reverse("anticonstitucionalmente") as INVERTIDA;
select reverse("reconocer") as INVERTIDA;

## FORMAT
-- le da formato a un numero con la cantidad de decimales y en formato de miles
select format(12332.1, 4) as FORMATEANDO;

## REPEAT
select repeat ("apt ", 6) as CORO;

## INSERT
-- inserta una subcadena en una posición específica
select insert("Hola Mundo", 5, 0, "mi "), insert("Hola Mundo", 5, 3, "mi ");

# ejercicio 
-- en una base de datos hay campo que contiene el nombre completo de un cliente
-- escribir la consulta que permite extraer el primer apellido
-- todos los registros tienen el formato nombre segundo_nombre apellido segundo_apellido
 
select substr("Ada Maria Rodriguez Ferrer", 
			locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)+1, 
			(locate(" ", "Ada Maria Rodriguez Ferrer",
				locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)+1)) - 
			locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)-1);
		
select locate(" ", "Ada Maria Rodriguez Ferrer",
		locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)+1);
	
select locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)+1 - 
			(locate(" ", "Ada Maria Rodriguez Ferrer",
				locate(" ", "Ada Maria Rodriguez Ferrer", locate(" ", "Ada Maria Rodriguez Ferrer")+1)+1));


-- Ejercicio 2
-- Extrae las tres últimas letras de cada nombre de ciudad en la base de datos 
-- world y muéstralas junto con el nombre original.
			
-- Ejercicio 3
-- Extrae el segundo nombre de los paises de la base de datos world
			
# LAPD
-- Rellena una cadena a la izquierda con un caracter específico
select lpad("Campus", 10, "*");

# LAPD
-- Rellena una cadena a la derecha con un caracter específico
select rpad("Campus", 10, "*");

# count
-- Cuenta la cantidad de registros de una consulta
select count(C.name) from country C; 

# sum
-- suma los valores de toda una columna
select sum(c.Population) as "Población mundial" from country c;

# avg
-- calcula el promedio de los valores de toda una columna
select format(avg(c.Population),0) as "Población mundial" from country c;

# max y min
-- Encuentra el valor máximo y mínimo de una columna
select max(country.Population) from country;

# Date_format()
-- formatea las fecha
select date_format(now(), "%d/%m/%Y") from country;

# Now()
-- Devuelve la fecha y hora actual
select now();

# round()
-- redondea un número;
select round(avg(c.Population),2) as "Población mundial" from country c;

# ifnull()
-- devuelve un valor alternativo si el valor del campo es nulo
select ifnull(c.IndepYear, "N/A") from country c;

-- IF()
-- if(condicion, valor_verdadero, valor_falso)
-- Listar los paises de "Sur América" e indicar si estan despoblados o poblados
-- Si la poblacion es menor 20 millones está despoblado
-- Si la población esta entre 20 y 40 millones esta poblado
-- Si la población es mayor a 40 millones está superpoblado 
select name, format(population, 0) as poblacion,
	if(population < 20000000, "Despoblado", 
		if(population < 40000000, "Poblado", "Superpoblado" )) as Estado
from country c 
where region = "South America";

#Ejercicios
-- 1. Calcular la densidad de población de los paises de América.
-- Si la densidad de población es mayor a 30 h/Km2 entonces mostrar que
-- esta muy poblado, si esta entre [20 y 30] h/km2 entonces está poblado
-- y si está entre [10 y 20) h/km2 esta poco poblado
-- por ultimo, los paises con densidad menor a 10 h/km2 estan despoblado.
-- ordenar este listado por el estado de la densidad de población

-- Gestion de Datos con SQL 

-- 1. Creacion de Tablas a partir de consultas
use world;

-- crear tabla paises_suramerica
create table paises_suramerica as
select c.code, c.Name, c.Population 
from country c
where c.Region = "South America";

show tables;
describe paises_suramerica;
select * from paises_suramerica;

-- crear una tabla en memoria a partir de una consulta
create table paises_suracas
engine = memory 
as 
select c.code, c.Name, c.Population 
from country c
where c.Region = "South America";
show tables;
show table status like "paises_suramerica";
---
use Biblioteca;
create table libro (
	id int primary key,
	titulo varchar(100),
	autor varchar(100)
);

create table prestamo(
	id int primary key auto_increment,
	id_libro int,
	fecha_prestamo date,
	fecha_entrega date,
	foreign key (id_libro) references libro(id)
);

-- Insertar libros
insert into libro values(1, "El principito", "anonimo");
insert into libro values(2, "Juan Salvador Gaviota", "Richard Bach");
INSERT INTO libro (id, titulo, autor) VALUES
(11, 'Cien años de soledad', 'Gabriel García Márquez'),
(12, '1984', 'George Orwell'),
(3, 'Don Quijote de la Mancha', 'Miguel de Cervantes'),
(4, 'El Principito', 'Antoine de Saint-Exupéry'),
(5, 'Crónica de una muerte anunciada', 'Gabriel García Márquez'),
(6, 'Rayuela', 'Julio Cortázar'),
(7, 'Moby Dick', 'Herman Melville'),
(8, 'Orgullo y prejuicio', 'Jane Austen'),
(9, 'Ulises', 'James Joyce'),
(10, 'Fahrenheit 451', 'Ray Bradbury');

