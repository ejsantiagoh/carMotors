USE CarMotorsDB;

-- Tabla: Supplier
CREATE TABLE Supplier (
    id_supplier INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    nit VARCHAR(20),
    contact_info TEXT,
    supply_frequency VARCHAR(50)
);

-- Tabla: Batch
CREATE TABLE Batch (
    id_batch INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT,
    entry_date DATE,
    FOREIGN KEY (supplier_id) REFERENCES Supplier(id_supplier)
);

-- Tabla: SparePart
CREATE TABLE SparePart (
    id_spare_part INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type ENUM('Mechanical', 'Electrical', 'Bodywork', 'Consumable'),
    brand VARCHAR(50),
    model VARCHAR(50),
    supplier_id INT,
    stock_quantity INT,
    minimum_stock INT,
    entry_date DATE,
    estimated_lifespan INT,
    status ENUM('Available', 'Reserved', 'Out_of_service'),
    batch_id INT,
    FOREIGN KEY (supplier_id) REFERENCES Supplier(id_supplier),
    FOREIGN KEY (batch_id) REFERENCES Batch(id_batch)
);

-- Tabla: Customer
CREATE TABLE Customer (
    id_customer INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    identification VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(200)
);

-- Tabla: Vehicle
CREATE TABLE Vehicle (
    id_vehicle INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    plate VARCHAR(20),
    type VARCHAR(50),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES Customer(id_customer)
);

-- Tabla: Service
CREATE TABLE Service (
    id_service INT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('Preventive', 'Corrective'),
    vehicle_id INT,
    description TEXT,
    estimated_time TIME,
    labor_cost DECIMAL(10, 2),
    status ENUM('Pending', 'In_progress', 'Completed', 'Delivered'),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id_vehicle)
);

-- Tabla: ServiceDetail
CREATE TABLE ServiceDetail (
    id_service_detail INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT,
    spare_part_id INT,
    quantity_used INT,
    FOREIGN KEY (service_id) REFERENCES Service(id_service),
    FOREIGN KEY (spare_part_id) REFERENCES SparePart(id_spare_part)
);

-- Tabla: Invoice
CREATE TABLE Invoice (
    id_invoice INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT,
    customer_id INT,
    date DATE,
    subtotal DECIMAL(10, 2),
    tax DECIMAL(10, 2),
    total DECIMAL(10, 2),
    cufe_code VARCHAR(100),
    qr_code_url TEXT,
    pdf_url TEXT,
    FOREIGN KEY (service_id) REFERENCES Service(id_service),
    FOREIGN KEY (customer_id) REFERENCES Customer(id_customer)
);

-- Tabla: Campaign
CREATE TABLE Campaign (
    id_campaign INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    discount_percentage DECIMAL(5, 2),
    start_date DATE,
    end_date DATE
);

-- Tabla: Inspection
CREATE TABLE Inspection (
    id_inspection INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT,
    inspection_date DATE,
    result ENUM('Approved', 'Repair_needed', 'Rejected'),
    next_inspection_date DATE,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id_vehicle)
);

-- Tabla: SupplierEvaluation
CREATE TABLE SupplierEvaluation (
    id_supplier_evaluation INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT,
    punctuality_score INT,
    quality_score INT,
    cost_score INT,
    evaluation_date DATE,
    FOREIGN KEY (supplier_id) REFERENCES Supplier(id_supplier)
);

-- Tabla: CampaignVehicle (para asociar campañas a vehículos/clientes)
CREATE TABLE CampaignVehicle (
    id_campaign_vehicle INT AUTO_INCREMENT PRIMARY KEY,
    campaign_id INT,
    vehicle_id INT,
    assigned_date DATE,
    FOREIGN KEY (campaign_id) REFERENCES Campaign(id_campaign),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id_vehicle)
);

-- Tabla: Reminder (para programar recordatorios automáticos)
CREATE TABLE Reminder (
    id_reminder INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    vehicle_id INT,
    reminder_date DATE,
    description TEXT,
    status ENUM('Pending', 'Sent', 'Acknowledged'),
    FOREIGN KEY (customer_id) REFERENCES Customer(id_customer),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id_vehicle)
);
