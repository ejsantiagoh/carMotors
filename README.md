
## 🚗🔧 CarMotors

Sistema de Gestión para Taller Automotriz

### 📌 Descripción General

CarMotors es una aplicación de escritorio desarrollada en Java con Maven, basada en arquitectura MVC por componentes. Está diseñada para optimizar y automatizar los procesos operativos de un taller automotriz.

El sistema permite gestionar de forma eficiente el inventario de repuestos, servicios de mantenimiento, atención al cliente, facturación electrónica, proveedores y generación de reportes, todo desde una interfaz gráfica amigable y bien organizada.

### ⚙️ Tecnologías Utilizadas

    ☕ Java 17

    🛠️ Maven

    🗄️ MySQL

    🖼️ Swing (Interfaz gráfica)

    📝 Log4J (Logging)

    🧩 DBeaver (Visualización y gestión de bases de datos)

    🔄 Git & GitHub (Control de versiones)


### 🗂️ Estructura del Proyecto

El proyecto está organizado en paquetes modulares, separando cada componente en su respectivo Modelo, Vista y Controlador:


📁 customers/       → Gestión de clientes  
📁 invoicing/       → Facturación electrónica  
📁 services/        → Servicios y mantenimientos  
📁 inventory/       → Gestión de repuestos  
📁 suppliers/       → Gestión de proveedores  
📁 reports/         → Generación de reportes

### 📋 Requisitos para Ejecutar el Proyecto

Instalar Java 17, Maven y MySQL.

Clonar el repositorio en tu equipo.

Crear una carpeta llamada config en la raíz del proyecto.

Dentro de esa carpeta, crear un archivo llamado database.properties con el siguiente contenido (ajusta según tu entorno):

    Ejemplo:

    db.url=jdbc:mysql://localhost:3306/CarMotors  
    db.username=root  
    db.password=campus2023

## 🧠 Funcionalidades Principales

### 🔧 1. Gestión de Inventarios
Registro, edición y eliminación de repuestos

Clasificación por tipo, marca, proveedor y estado

Control de stock y alertas por niveles bajos

Trazabilidad de lotes y seguimiento de vida útil

### 🛠️ 2. Gestión de Servicios
Registro de mantenimientos preventivos y correctivos

Seguimiento del estado del servicio

Asignación de técnicos

Historial detallado por vehículo

### 👥 3. Gestión de Clientes
Registro de datos personales

Historial de servicios prestados

Alertas para próximos mantenimientos

### 💰 4. Facturación Electrónica
Generación de facturas (PDF / imagen)

Cumplimiento de la resolución DIAN 042 de 2020

Códigos CUFE, QR y firma digital

Detalle de servicios, repuestos e impuestos aplicados

### 🏭 5. Proveedores
Registro de proveedores y productos suministrados

Control de visitas y evaluación de desempeño

Integración con la gestión de inventario

### 📈 6. Reportes
Estadísticas por cliente, ingresos, repuestos

Análisis de consumo y tendencias

Medición de productividad del personal

## 🧑‍💻 Aportes del Equipo

### 👨‍💻 Eimer

Configuración inicial de Maven, Java y conexión MySQL

Creación de estructura base y patrones de diseño

Desarrollo de módulos: Clientes, Servicios y Facturación

Navegación dinámica con CardLayout

Integración final de vistas y controladores

Reportes iniciales y pruebas funcionales

### 👩‍💻 Laia

Diseño de la base de datos y diagrama ER

CRUD completo de Repuestos y Proveedores

Mejora de DatabaseConnection con archivo externo

Creación de InventoryManagementService con alertas de stock

Búsqueda y filtros en inventario

Diagrama de clases, validaciones y documentación técnica

Gestión del repositorio Git y resolución de conflictos



