drop database prueba;

create database prueba;

use prueba;

create table clientes(
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

explain select c.nombre, p.producto, p.cantidad
from clientes c, pedidos p
where c.id = p.cliente_id;

select c.nombre, p.producto, p.cantidad
from clientes c
join pedidos p on c.id = p.cliente_id
where p.cantidad > 0;

-- 7. Caching (cachear) de consultas

-- 8, Particionamiento de tablas
-- Estrategias
-- Rangos (valores: fechas, numeros)
-- Lista (Lista de valores)
-- Hash
-- key similar hash

CREATE TABLE transacciones (
    id int NOT NULL AUTO_INCREMENT,
    cuenta_id int NOT NULL,
    monto decimal(10, 2) NOT NULL,
    tipo enum("depósito", "retiro", "transferencia") NOT NULL,
    fecha_transaccion date NOT NULL,
    descripcion varchar(200),
    PRIMARY KEY(id, fecha_transaccion)
)
PARTITION BY range(YEAR(fecha_transaccion))
    (
        PARTITION p2021 VALUES less than (2022),
        PARTITION p2022 VALUES less than (2023),
        PARTITION p2023 VALUES less than (2024),
        PARTITION p2024 VALUES less than (2025),
        PARTITION pmax VALUES less than MAXVALUE
    );

INSERT INTO transacciones (cuenta_id, monto, tipo, fecha_transaccion, descripcion) VALUES
(101, 1500.00, 'deposito', '2021-05-10', 'Depósito inicial'),
(102, 2500.00, 'deposito', '2021-08-20', 'Depósito de nómina'),
(101, 500.00, 'retiro', '2022-01-15', 'Retiro ATM'),
(103, 3000.00, 'deposito', '2022-06-10', 'Depósito de venta'),
(102, 1000.00, 'transferencia', '2023-02-28', 'Transferencia a cuenta de ahorros'),
(104, 2000.00, 'deposito', '2023-07-15', 'Depósito de bono'),
(103, 800.00, 'retiro', '2024-01-05', 'Retiro para gastos'),
(101, 1200.00, 'deposito', '2024-03-20', 'Depósito de devolución');

explain select  id, fecha_transaccion, cuenta_id
from transacciones 
where fecha_transaccion between "2023-01-01" and "2023-12-31";

select 
	table_schema,
	table_name,
	partition_name,
	partition_ordinal_position,
	table_rows
from 
	information_schema.partitions
where
	table_name = "transacciones";

-- 9 Ajustar parametros en servidorr de MySQL (tunning)

-- Buffer
SHOW variables LIKE 'innodb_buffer_pool_size';

SET GLOBAL innodb_buffer_pool_size = 268435456;

-- performance_schema : nos permite optimizar consultas que se repiten
SET GLOBAL performance_schema = ON;

-- configuración en el archivo de configuración de MySQL my.cnf

-- 10. Optimizar los tipos de datos

-- Antes: Esquema ineficiente
CREATE TABLE clientes_ineficiente (
    id VARCHAR(36),  -- UUID como cadena
    nombre VARCHAR(255),  -- Demasiado grande
    email VARCHAR(255),  -- Demasiado grande
    telefono VARCHAR(255),  -- Demasiado grande
    fecha_registro DATETIME,  -- Ineficiente para almacenar solo la fecha
    activo CHAR(1),  -- Ineficiente para valores booleanos
    notas TEXT,  -- Para almacenar pequeños comentarios
    PRIMARY KEY (id)
);

-- Después: Esquema optimizado
CREATE TABLE clientes_optimizado (
    id INT UNSIGNED AUTO_INCREMENT,
    nombre VARCHAR(100),  -- Tamaño más realista
    email VARCHAR(100),  -- Tamaño más realista
    telefono VARCHAR(20),  -- Tamaño más realista
    fecha_registro DATE,  -- Solo necesitamos la fecha
    activo BOOLEAN,  -- Más eficiente para valores booleanos
    notas VARCHAR(500),  -- Limitado a 500 caracteres si es suficiente
    PRIMARY KEY (id),
    UNIQUE KEY (email)  -- Índice útil si se busca por email
);

-- 11. Herramientas externas
-- Oracle: MySQL Enterprise Monitor
-- Percona Monitoring and Managment

-- 12. Replicacion de la base de datos

drop table transacciones;