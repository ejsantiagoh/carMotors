
USE CarMotors;

-- Tabla: Dirección / Address
INSERT INTO address (street, city) VALUES ('123 Main St', 'Springfield');
INSERT INTO address (street, city) VALUES ('456 Elm St', 'Shelbyville');
INSERT INTO address (street, city) VALUES ('789 Oak St', 'Ogdenville');

-- Tabla: Cliente / Customer
INSERT INTO customer (name, identification, phone, email, address_id)
VALUES ('John Doe', '12345678', '555-1234', 'john@example.com', 1),
       ('Jane Smith', '87654321', '555-5678', 'jane@example.com', 2);

-- Tabla: Vehículo / Vehicle
INSERT INTO vehicle (brand, model, plate, customer_id)
VALUES ('Toyota', 'Corolla', 'ABC123', 1),
       ('Ford', 'Focus', 'XYZ789', 2);

-- Tabla: Técnico / Technician
INSERT INTO technician (name, specialty)
VALUES ('Alice Johnson', 'Engine'),
       ('Bob Brown', 'Transmission');

-- Tabla: Servicio / Service
INSERT INTO service (type, vehicle_id, description, estimated_time, labor_cost, status)
VALUES ('Oil Change', 1, 'Routine oil change', 2, 50.00, 'Pending'),
       ('Brake Repair', 2, 'Front brake pads replacement', 3, 150.00, 'Completed');

-- Tabla: Servicio-Técnico / Service-Technician
INSERT INTO service_technician (service_id, technician_id)
VALUES (1, 1),
       (2, 2);

-- Tabla: Recordatorio / Reminder
INSERT INTO reminder (customer_id, vehicle_id, reminder_date, description, status)
VALUES (1, 1, CURDATE(), 'Next oil change', 'Active'),
       (2, 2, CURDATE() + INTERVAL 30 DAY, 'Annual inspection', 'Scheduled');

-- Tabla: Proveedor / Supplier
INSERT INTO supplier (name, nit, contact_info, supply_frequency)
VALUES ('AutoParts Inc.', '900123', 'contact@autoparts.com', 'Monthly'),
       ('BrakeWorld', '900456', 'support@brakeworld.com', 'Biweekly');

-- Tabla: Lote / Batch
INSERT INTO batch (supplier_id, entry_date)
VALUES (1, CURDATE() - INTERVAL 5 DAY),
       (2, CURDATE());

-- Tabla: Repuesto / Spare Part
INSERT INTO spare_part (name, type, brand, model, supplier_id, stock_quantity, minimum_stock, estimated_lifespan, status, batch_id)
VALUES ('Oil Filter', 'Filter', 'Bosch', 'OF123', 1, 50, 10, 180, 'Available', 1),
       ('Brake Pad', 'Brake', 'Brembo', 'BP456', 2, 30, 5, 365, 'Available', 2);

-- Tabla: Evaluación de Proveedor / Supplier Evaluation
INSERT INTO supplier_evaluation (supplier_id, punctuality_score, quality_score, cost_score, evaluation_date)
VALUES (1, 5, 4, 4, CURDATE()),
       (2, 4, 5, 5, CURDATE());

-- Tabla: Detalle del Servicio / Service Detail
INSERT INTO service_detail (service_id, spare_part_id, quantity_used)
VALUES (1, 1, 1),
       (2, 2, 2);

-- Tabla: Factura / Invoice
INSERT INTO invoice (service_id, customer_id, date, subtotal, tax, total, cufe_code, qr_code_url, pdf_url)
VALUES (1, 1, CURDATE(), 50.00, 9.50, 59.50, 'CUFE123', 'http://example.com/qr1.png', 'http://example.com/invoice1.pdf'),
       (2, 2, CURDATE(), 150.00, 28.50, 178.50, 'CUFE456', 'http://example.com/qr2.png', 'http://example.com/invoice2.pdf');

-- Tabla: Detalle de Factura / Invoice Detail
INSERT INTO invoice_detail (invoice_id, spare_part_id, quantity, price)
VALUES (1, 1, 1, 50.00),
       (2, 2, 1, 150.00);
