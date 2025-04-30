-- Ejercicio 1

CREATE TABLE Region (
    CodRegion INT PRIMARY KEY,
    Nombre VARCHAR(100)
);

CREATE TABLE Provincia (
    CodProvincia INT PRIMARY KEY,
    Nombre VARCHAR(100),
    CodRegion INT,
    FOREIGN KEY (CodRegion) REFERENCES Region(CodRegion)
);

CREATE TABLE Localidad (
    CodLocalidad INT PRIMARY KEY,
    Nombre VARCHAR(100),
    CodProvincia INT,
    FOREIGN KEY (CodProvincia) REFERENCES Provincia(CodProvincia)
);

CREATE TABLE Empleado (
    ID INT PRIMARY KEY,
    DNI VARCHAR(20) UNIQUE,
    Nombre VARCHAR(100),
    FechaNac DATE,
    Telefono VARCHAR(20),
    Salario DECIMAL(10,2),
    CodLocalidad INT,
    FOREIGN KEY (CodLocalidad) REFERENCES Localidad(CodLocalidad)
);

-- Ejercicio 2

CREATE TABLE Cliente (
    CodCliente INT PRIMARY KEY,
    DNI VARCHAR(20) UNIQUE,
    Nombre VARCHAR(100),
    Direccion VARCHAR(255),
    Telefono VARCHAR(20)
);

CREATE TABLE Reserva (
    Numero INT PRIMARY KEY,
    FechaInicio DATE,
    FechaFin DATE,
    PrecioTotal DECIMAL(10,2),
    CodCliente INT,
    FOREIGN KEY (CodCliente) REFERENCES Cliente(CodCliente)
);

CREATE TABLE Coche (
    Matrícula VARCHAR(20) PRIMARY KEY,
    Marca VARCHAR(50),
    Modelo VARCHAR(50),
    Color VARCHAR(30),
    PrecioHora DECIMAL(10,2)
);

CREATE TABLE Incluye (
    Numero INT,
    Matricula VARCHAR(20),
    litrosGas DECIMAL(5,2),
    PRIMARY KEY (Numero, Matricula),
    FOREIGN KEY (Numero) REFERENCES Reserva(Numero),
    FOREIGN KEY (Matricula) REFERENCES Coche(Matricula)
);

CREATE TABLE Avala (
    Avalado INT,
    Avalador INT,
    PRIMARY KEY (Avalado, Avalador),
    FOREIGN KEY (Avalado) REFERENCES Cliente(CodCliente),
    FOREIGN KEY (Avalador) REFERENCES Cliente(CodCliente)
);


-- Ejercicio 3
CREATE TABLE Sucursal (
    Codigo INT PRIMARY KEY,
    Direccion VARCHAR(255),
    Telefono VARCHAR(20)
);
CREATE TABLE Empleado (
    DNI VARCHAR(20) PRIMARY KEY,
    Nombre VARCHAR(100),
    Direccion VARCHAR(255),
    Telefono VARCHAR(20),
    Sucursal INT,
    FOREIGN KEY (Sucursal) REFERENCES Sucursal(Codigo)
);
CREATE TABLE Periodista (
    DNI VARCHAR(20) PRIMARY KEY,
    Nombre VARCHAR(100),
    Direccion VARCHAR(255),
    Telefono VARCHAR(20),
    Especialista VARCHAR(50)
);
CREATE TABLE Revista (
    NumReg INT PRIMARY KEY,
    Titulo VARCHAR(100),
    Periodicidad VARCHAR(50),
    Tipo VARCHAR(50),
    Sucursal INT,
    FOREIGN KEY (Sucursal) REFERENCES Sucursal(Codigo)
);
CREATE TABLE NumRevista (
    NumReg INT,
    Numero INT,
    NumPaginas INT,
    Fecha DATE,
    CantVendidas INT,
    PRIMARY KEY (NumReg, Numero),
    FOREIGN KEY (NumReg) REFERENCES Revista(NumReg)
);
CREATE TABLE escribe (
    NumReg INT,
    DNI_Per VARCHAR(20),
    PRIMARY KEY (NumReg, DNI_Per),
    FOREIGN KEY (NumReg) REFERENCES Revista(NumReg),
    FOREIGN KEY (DNI_Per) REFERENCES Periodista(DNI)
);
