/*
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to change this license
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to edit this template
 */
package main.java.com.carmotors.inventory;

/*
 *
 * @author yorle
 */

// Spare Part DAO

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SparePartDAO {
    private String url;
    private String username;
    private String password;

    public SparePartDAO() {
        // Cargar las propiedades desde database.properties
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/database.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo database.properties en el classpath");
            }
            props.load(input);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");

            if (url == null || username == null || password == null) {
                throw new RuntimeException("Faltan propiedades en database.properties: db.url, db.username o db.password");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las propiedades de la base de datos: " + e.getMessage(), e);
        }
    }

    public void saveSparePart(SparePart sparePart) throws SQLException {
        String sql = "INSERT INTO spare_part (name, type, brand, model, supplier_id, stock_quantity, minimum_stock, estimated_lifespan, status, batch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
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