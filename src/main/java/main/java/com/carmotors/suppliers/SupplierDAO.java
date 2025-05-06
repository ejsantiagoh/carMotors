/*
 * Click https://www.apache.org/licenses/LICENSE-2.0.txt to change this license
 * Click https://www.netbeans.org/about/legal/license.html to edit this template
 */
package main.java.com.carmotors.suppliers;

import main.java.com.carmotors.common.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yorle
 */
public class SupplierDAO {
    private Connection conn;

    public SupplierDAO() {
        // Usar DatabaseConnection para obtener la conexión
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void saveSupplier(Supplier supplier) throws SQLException {
        String insertSQL = "INSERT INTO supplier (name, nit, contact_info, supply_frequency) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE supplier SET name = ?, contact_info = ?, supply_frequency = ? WHERE nit = ?";

        // Verificar si el proveedor ya existe por NIT
        String checkSQL = "SELECT supplier_id FROM supplier WHERE nit = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
            checkStmt.setString(1, supplier.getNit());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // Actualizar proveedor existente
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setString(1, supplier.getName());
                    updateStmt.setString(2, supplier.getContact());
                    updateStmt.setString(3, supplier.getVisitFrequency());
                    updateStmt.setString(4, supplier.getNit());
                    updateStmt.executeUpdate();
                }
            } else {
                // Insertar nuevo proveedor
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, supplier.getName());
                    insertStmt.setString(2, supplier.getNit());
                    insertStmt.setString(3, supplier.getContact());
                    insertStmt.setString(4, supplier.getVisitFrequency());
                    insertStmt.executeUpdate();

                    // Obtener el ID generado
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        supplier.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT s.supplier_id, s.name, s.nit, s.contact_info, s.supply_frequency, " +
                "se.punctuality_score, se.quality_score, se.cost_score " +
                "FROM supplier s " +
                "LEFT JOIN supplier_evaluation se ON s.supplier_id = se.supplier_id " +
                "AND se.evaluation_date = (SELECT MAX(evaluation_date) FROM supplier_evaluation WHERE supplier_id = s.supplier_id)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("nit"),
                        rs.getString("contact_info"),
                        rs.getString("supply_frequency")
                );
                // Manejar valores nulos para los puntajes
                supplier.setPunctualityScore(rs.getInt("punctuality_score") != 0 ? rs.getInt("punctuality_score") : 0);
                supplier.setQualityScore(rs.getInt("quality_score") != 0 ? rs.getInt("quality_score") : 0);
                supplier.setCostScore(rs.getInt("cost_score") != 0 ? rs.getInt("cost_score") : 0);
                // Cargar productos suministrados
                supplier.getSuppliedProducts().addAll(getSuppliedProductsBySupplierId(supplier.getId()));
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    public void updateEvaluation(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier_evaluation (supplier_id, punctuality_score, quality_score, cost_score, evaluation_date) " +
                     "VALUES (?, ?, ?, ?, CURRENT_DATE) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "punctuality_score = VALUES(punctuality_score), " +
                     "quality_score = VALUES(quality_score), " +
                     "cost_score = VALUES(cost_score), " +
                     "evaluation_date = CURRENT_DATE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getId());
            stmt.setInt(2, supplier.getPunctualityScore());
            stmt.setInt(3, supplier.getQualityScore());
            stmt.setInt(4, supplier.getCostScore());
            stmt.executeUpdate();
        }
    }

    public void saveSuppliedProduct(Supplier supplier, SuppliedProduct product) throws SQLException {
        String sql = "INSERT INTO spare_part (name, type, brand, model, supplier_id, stock_quantity, minimum_stock, estimated_lifespan, status, batch_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getType()); // Usamos product_type como name
            stmt.setString(2, product.getType()); // Usamos product_type como type
            stmt.setString(3, null); // brand (puede ser null)
            stmt.setString(4, null); // model (puede ser null)
            stmt.setInt(5, supplier.getId()); // supplier_id
            stmt.setInt(6, product.getQuantity()); // stock_quantity
            stmt.setInt(7, 5); // minimum_stock (valor por defecto)
            stmt.setInt(8, 180); // estimated_lifespan (valor por defecto)
            stmt.setString(9, "Available"); // status (valor por defecto)
            
            // Crear un nuevo batch con la fecha proporcionada por el usuario
            String insertBatchSQL = "INSERT INTO batch (supplier_id, entry_date) VALUES (?, ?)";
            int batchId;
            try (PreparedStatement batchStmt = conn.prepareStatement(insertBatchSQL, Statement.RETURN_GENERATED_KEYS)) {
                batchStmt.setInt(1, supplier.getId());
                batchStmt.setDate(2, new java.sql.Date(product.getSupplyDate().getTime()));
                batchStmt.executeUpdate();
                ResultSet batchKeys = batchStmt.getGeneratedKeys();
                if (batchKeys.next()) {
                    batchId = batchKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del batch.");
                }
            }
            stmt.setInt(10, batchId); // batch_id

            stmt.executeUpdate();
        }
    }

    public List<SuppliedProduct> getSuppliedProductsBySupplierId(int supplierId) throws SQLException {
        List<SuppliedProduct> products = new ArrayList<>();
        String sql = "SELECT sp.type, sp.stock_quantity, b.entry_date " +
                     "FROM spare_part sp " +
                     "JOIN batch b ON sp.batch_id = b.id_batch " +
                     "WHERE sp.supplier_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SuppliedProduct product = new SuppliedProduct(
                            rs.getString("type"),
                            rs.getInt("stock_quantity"),
                            rs.getDate("entry_date")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }

    // Método auxiliar para obtener el batch más reciente
    private int getLatestBatchId(int supplierId) throws SQLException {
        String sql = "SELECT id_batch FROM batch WHERE supplier_id = ? ORDER BY entry_date DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_batch");
                }
            }
        }
        return 0; // Si no hay batch, devolver 0
    }
}