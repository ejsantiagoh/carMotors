/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fashe
 */


public class ServiceManager {
    private final ServiceDAO serviceDAO;

    public ServiceManager() {
        this.serviceDAO = new ServiceDAO();
    }

    public void refreshServiceList(DefaultTableModel tableModel) {
        try {
            List<Service> services = serviceDAO.listServices();
            tableModel.setRowCount(0); // Limpiar la tabla
            if (services == null || services.isEmpty()) {
                tableModel.addRow(new Object[]{"No hay servicios registrados.", "", "", "", "", "", ""});
            } else {
                for (Service service : services) {
                    tableModel.addRow(new Object[]{
                        service.getId(),
                        service.getType(),
                        service.getVehicleId(),
                        service.getDescription(),
                        service.getEstimatedTime(),
                        service.getLaborCost(),
                        service.getStatus()
                    });
                }
            }
        } catch (RuntimeException ex) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"Error al listar servicios: " + ex.getMessage(), "", "", "", "", "", ""});
        }
    }
}