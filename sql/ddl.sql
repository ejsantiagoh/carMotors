CREATE DATABASE IF NOT EXISTS CarMotors;
USE CarMotors;

-- Table for Address
CREATE TABLE address (
    id_address INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL
);

-- Table for Customer
CREATE TABLE customer (
    id_customer INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    identification VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(20),
    email VARCHAR(100),
    address_id INT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address(id_address)
);

-- Table for Vehicle
CREATE TABLE vehicle (
    id_vehicle INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    plate VARCHAR(20) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id_customer)
);

-- Table for Technician
CREATE TABLE technician (
    id_technician INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(50) NOT NULL
);

-- Table for Service
CREATE TABLE service (
    id_service INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    vehicle_id INT NOT NULL,
    description TEXT,
    estimated_time INT NOT NULL,
    labor_cost DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id_vehicle)
);

-- Table for Service-Technician (Many-to-Many Relationship)
CREATE TABLE service_technician (
    service_id INT NOT NULL,
    technician_id INT NOT NULL,
    PRIMARY KEY (service_id, technician_id),
    FOREIGN KEY (service_id) REFERENCES service(id_service),
    FOREIGN KEY (technician_id) REFERENCES technician(id_technician)
);

-- Table for Reminder
CREATE TABLE reminder (
    id_reminder INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    reminder_date DATE NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id_customer),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id_vehicle)
);

-- Table for Supplier
CREATE TABLE supplier (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nit VARCHAR(50) NOT NULL UNIQUE,
    contact_info VARCHAR(255),
    supply_frequency VARCHAR(50)
);

-- Table for Batch
CREATE TABLE batch (
    id_batch INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    entry_date DATE NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id)
);

-- Table for SparePart
CREATE TABLE spare_part (
    id_spare_part INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    brand VARCHAR(50),
    model VARCHAR(50),
    supplier_id INT NOT NULL,
    stock_quantity INT NOT NULL,
    minimum_stock INT NOT NULL,
    estimated_lifespan INT,
    status VARCHAR(20) NOT NULL,
    batch_id INT NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (batch_id) REFERENCES batch(id_batch)
);

-- Table for SupplierEvaluation
CREATE TABLE supplier_evaluation (
    id_supplier_evaluation INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    punctuality_score INT NOT NULL CHECK (punctuality_score BETWEEN 1 AND 5),
    quality_score INT NOT NULL CHECK (quality_score BETWEEN 1 AND 5),
    cost_score INT NOT NULL CHECK (cost_score BETWEEN 1 AND 5),
    evaluation_date DATE NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id)
);

-- Table for ServiceDetail
CREATE TABLE service_detail (
    id_service_detail INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT NOT NULL,
    spare_part_id INT NOT NULL,
    quantity_used INT NOT NULL,
    FOREIGN KEY (service_id) REFERENCES service(id_service),
    FOREIGN KEY (spare_part_id) REFERENCES spare_part(id_spare_part)
);

-- Table for Invoice
CREATE TABLE invoice (
    id_invoice INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT NOT NULL,
    customer_id INT NOT NULL,
    date DATE NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    cufe_code VARCHAR(100) NOT NULL,
    qr_code_url VARCHAR(255),
    pdf_url VARCHAR(255),
    FOREIGN KEY (service_id) REFERENCES service(id_service),
    FOREIGN KEY (customer_id) REFERENCES customer(id_customer)
);

-- Table for InvoiceDetail
CREATE TABLE invoice_detail (
    id_invoice_detail INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    spare_part_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id_invoice),
    FOREIGN KEY (spare_part_id) REFERENCES spare_part(id_spare_part)
);