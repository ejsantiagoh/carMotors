drop database prueba;

create database prueba;

use prueba;

create table cientes(
	id int primary key auto_increment,
	nombre varchar(50),
	email varchar(100),
	ciudad varchar(50),
	fec_reg date
);

INSERT INTO clientes (nombre, email, ciudad, fec_reg) VALUES
('Juan Pérez', 'juan@ejemplo.com', 'Madrid', '2023-01-15'),
('María López', 'maria@ejemplo.com', 'Barcelona', '2023-02-20'),
('Carlos Ruiz', 'carlos@ejemplo.com', 'Sevilla', '2023-03-10'),
('Ana Martínez', 'ana@ejemplo.com', 'Valencia', '2023-04-05');

explain select * from clientes where ciudad = "Madrid";

-- 1. CREAR INDICES
create index idx_ciudad on clientes(ciudad);

-- 2. EVITE select *
explain select * from clientes where ciudad = "Madrid";

select id, nombre from clientes where ciudad = "Madrid"

-- 3. Limitar los resultados
select id, nombre from clientes where ciudad = "Madrid" limit 100;

-- 4. Evitar funciones en columnas indexadas
explain select id, nombre from clientes where lower(ciudad) = lower("Madrid");

-- 5. Utilizar explain

-- 6. Optimizar Joins
create table pedidos(
	id int primary key auto_increment,
	cliente_id int,
	producto varchar(100),
	cantidad int,
	fecha_pedido date,
	foreign key (cliente_id) references clientes(id)
);

INSERT INTO pedidos (cliente_id, producto, cantidad, fecha_pedido) VALUES
(1, 'Laptop', 1, '2023-02-10'),
(2, 'Teléfono', 2, '2023-03-15'),
(1, 'Monitor', 1, '2023-04-20'),
(3, 'Tablet', 1, '2023-05-05');