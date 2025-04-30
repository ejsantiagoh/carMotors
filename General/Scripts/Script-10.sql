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

select * from clientes where ciudad = "Madrid";