/*
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to change this license
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to edit this template
 */
package main.java.com.carmotors.inventory;

import main.java.com.carmotors.common.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yorle
 */
public class SparePartDAO {
    private Connection conn;

    public SparePartDAO() {
        // Usar DatabaseConnection para obtener la conexión
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void saveSparePart(SparePart sparePart) throws SQLException {
        String sql = "INSERT INTO spare_part (name, type, brand, model, supplier_id, stock_quantity, minimum_stock, estimated_lifespan, status, batch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sparePart.getName());
            pstmt.setString(2, sparePart.getType());
            pstmt.setString(3, sparePart.getBrand());
            pstmt.setString(4, sparePart.getModel());
            pstmt.setInt(5, sparePart.getSupplierId());
            pstmt.setInt(6, sparePart.getStockQuantity());
            pstmt.setInt(7, sparePart.getMinStockLevel());
            pstmt.setInt(8, sparePart.getEstimatedLifespan());
            pstmt.setString(9, sparePart.getStatus());
            pstmt.setInt(10, sparePart.getBatchId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al guardar el repuesto: " + e.getMessage(), e);
        }
    }

    public List<SparePart> getAllSpareParts() throws SQLException {
        List<SparePart> spareParts = new ArrayList<>();
        String sql = "SELECT name, type, brand, model, supplier_id, stock_quantity, minimum_stock, estimated_lifespan, status, batch_id FROM spare_part";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SparePart sp = new SparePart(
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("supplier_id"),
                        null, // supplier_name no disponible
                        null, // contact_info no disponible
                        null, // supply_frequency no disponible
                        rs.getInt("stock_quantity"),
                        rs.getInt("minimum_stock"),
                        null, // entry_date no está
                        rs.getInt("estimated_lifespan"),
                        rs.getString("status"),
                        rs.getInt("batch_id")
                );
                spareParts.add(sp);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los repuestos: " + e.getMessage(), e);
        }
        return spareParts;
    }
}