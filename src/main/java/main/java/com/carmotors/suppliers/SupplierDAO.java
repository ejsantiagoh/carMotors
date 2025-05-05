/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        String checkSQL = "SELECT id_supplier FROM supplier WHERE nit = ?";
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
        String sql = "SELECT s.id_supplier, s.name, s.nit, s.contact_info, s.supply_frequency, " +
                "se.punctuality_score, se.quality_score, se.cost_score " +
                "FROM supplier s " +
                "LEFT JOIN supplier_evaluation se ON s.id_supplier = se.id_supplier " +
                "AND se.evaluation_date = (SELECT MAX(evaluation_date) FROM supplier_evaluation WHERE id_supplier = s.id_supplier)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("id_supplier"),
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
        String sql = "INSERT INTO supplier_evaluation (id_supplier, punctuality_score, quality_score, cost_score) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "punctuality_score = VALUES(punctuality_score), " +
                "quality_score = VALUES(quality_score), " +
                "cost_score = VALUES(cost_score)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getId());
            stmt.setInt(2, supplier.getPunctualityScore());
            stmt.setInt(3, supplier.getQualityScore());
            stmt.setInt(4, supplier.getCostScore());
            stmt.executeUpdate();
        }
    }

    public void saveSuppliedProduct(Supplier supplier, SuppliedProduct product) throws SQLException {
        String sql = "INSERT INTO supplied_product (supplier_id, product_type, quantity, supply_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getId());
            stmt.setString(2, product.getType());
            stmt.setInt(3, product.getQuantity());
            java.sql.Date sqlDate = new java.sql.Date(product.getSupplyDate().getTime());
            stmt.setDate(4, sqlDate);
            stmt.executeUpdate();
        }
    }

    public List<SuppliedProduct> getSuppliedProductsBySupplierId(int supplierId) throws SQLException {
        List<SuppliedProduct> products = new ArrayList<>();
        String sql = "SELECT product_type, quantity, supply_date FROM supplied_product WHERE supplier_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SuppliedProduct product = new SuppliedProduct(
                            rs.getString("product_type"),
                            rs.getInt("quantity"),
                            rs.getDate("supply_date")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
}