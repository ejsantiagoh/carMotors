/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import main.java.com.carmotors.common.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fashe
 */

public class ServiceDAO {
    public List<Service> listServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id_service"));
                service.setType(rs.getString("type"));
                service.setVehicleId(rs.getInt("vehicle_id"));
                service.setDescription(rs.getString("description"));
                service.setEstimatedTime(rs.getInt("estimated_time"));
                service.setLaborCost(rs.getDouble("labor_cost"));
                service.setStatus(rs.getString("status"));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar servicios", e);
        }
        return services;
    }
}