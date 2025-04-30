-- SUPPLIERS
-- Insertar información de proveedores
INSERT INTO Supplier (name, nit, contact_info, supply_frequency) VALUES
('AutoRepuestos S.A.', '800123456', 'autorpr@example.com', 'Weekly'),
('Energía Total Ltda.', '900456789', 'baterias@example.com', 'Biweekly');

-- BATCHES
-- Insertar lotes de ingreso de repuestos
INSERT INTO Batch (supplier_id, entry_date) VALUES
(1, '2025-04-01'),
(2, '2025-04-05');

-- SPARE PARTS
-- Insertar repuestos con detalles
INSERT INTO SparePart (name, type, brand, model, supplier_id, stock_quantity, minimum_stock, entry_date, estimated_lifespan, status, batch_id) VALUES
('Filtro de Aceite', 'Consumable', 'Toyota', 'Corolla', 1, 20, 5, '2025-04-01', 365, 'Available', 1),
('Batería 12V', 'Electrical', 'General', '2.5', 1, 15, 3, '2025-03-20', 730, 'Available', 1),
('Pastillas de Freno', 'Mechanical', 'Mazda', '3', 1, 10, 2, '2025-04-15', 365, 'Available', 1),
('Aceite 10W40', 'Consumable', 'General', '2', 2, 50, 10, '2025-04-10', 540, 'Available', 2),
('Espejo Lateral', 'Bodywork', 'Chevrolet', 'Spark', 1, 5, 2, '2025-04-05', 730, 'Available', 1);

-- CUSTOMERS
-- Insertar datos de clientes
INSERT INTO Customer (name, identification, phone, email, address) VALUES
('Carlos Ramírez', '1098765432', '3104567890', 'carlos.ramirez@mail.com', 'Calle 123 #45-67'),
('Ana Martínez', '1081234567', '3139876543', 'ana.martinez@mail.com', 'Carrera 45 #78-90'),
('Luis Torres', '1076543210', '3117896541', 'luis.torres@mail.com', 'Av. Siempre Viva 742');

-- VEHICLES
-- Insertar vehículos asociados a los clientes
INSERT INTO Vehicle (brand, model, plate, type, customer_id) VALUES
('Toyota', 'Corolla', 'ABC123', 'Sedan', 1),
('Chevrolet', 'Spark', 'XYZ789', 'Hatchback', 2),
('Mazda', '3', 'LMN456', 'Sedan', 3);

-- SERVICES
-- Insertar servicios realizados
INSERT INTO Service (type, vehicle_id, description, estimated_time, labor_cost, status) VALUES
('Preventive', 1, 'Cambio de aceite y revisión general', '02:00:00', 60000.00, 'Completed'),
('Corrective', 2, 'Reemplazo de espejo lateral', '01:30:00', 45000.00, 'Delivered');

-- SERVICE DETAILS
-- Insertar repuestos utilizados en los servicios
INSERT INTO ServiceDetail (service_id, spare_part_id, quantity_used) VALUES
(1, 1, 1),
(1, 4, 2),
(2, 5, 1);

-- INVOICES
-- Insertar facturas generadas
INSERT INTO Invoice (service_id, customer_id, date, subtotal, tax, total, cufe_code, qr_code_url, pdf_url) VALUES
(1, 1, '2025-04-27', 60000.00, 11400.00, 71400.00, 'cufe1234567890', 'https://dian.gov.co/factura/F001-0001', 'https://dian.gov.co/factura/F001-0001.pdf'),
(2, 2, '2025-04-28', 45000.00, 8550.00, 53550.00, 'cufe1234567891', 'https://dian.gov.co/factura/F001-0002', 'https://dian.gov.co/factura/F001-0002.pdf');

-- CAMPAIGNS
-- Insertar campañas promocionales
INSERT INTO Campaign (name, description, discount_percentage, start_date, end_date) VALUES
('Descuento Frenos', 'Campaña de descuento en frenos', 15.00, '2025-05-01', '2025-05-31'),
('Chequeo Gratis', 'Revisión gratis para clientes nuevos', 100.00, '2025-06-01', '2025-06-15');

-- INSPECTIONS
-- Insertar registros de inspecciones a vehículos
INSERT INTO Inspection (vehicle_id, inspection_date, result, next_inspection_date) VALUES
(1, '2025-04-25', 'Approved', '2026-04-25'),
(2, '2025-04-26', 'Repair_needed', '2025-05-26');

-- SUPPLIER EVALUATIONS
-- Insertar evaluaciones de proveedores
INSERT INTO SupplierEvaluation (supplier_id, punctuality_score, quality_score, cost_score, evaluation_date) VALUES
(1, 8, 9, 7, '2025-04-20'),
(2, 9, 8, 8, '2025-04-22');

-- CAMPAIGN VEHICLE
-- Insertar asignaciones de campañas a vehículos
INSERT INTO CampaignVehicle (campaign_id, vehicle_id, assigned_date) VALUES
(1, 1, '2025-05-01'),
(2, 3, '2025-06-01');

-- REMINDERS
-- Insertar recordatorios programados
INSERT INTO Reminder (customer_id, vehicle_id, reminder_date, description, status) VALUES
(1, 1, '2025-06-01', 'Recordatorio para cambio de aceite', 'Pending'),
(3, 3, '2025-06-10', 'Recordatorio revisión general', 'Pending');
