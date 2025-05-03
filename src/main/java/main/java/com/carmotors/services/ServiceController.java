/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fashe
 */

public class ServiceController {
    private final ServiceManager serviceManager;
    private final ServiceView serviceView;
    private final DefaultTableModel tableModel;

    public ServiceController(ServiceManager serviceManager, ServiceView serviceView, DefaultTableModel tableModel) {
        this.serviceManager = serviceManager;
        this.serviceView = serviceView;
        this.tableModel = tableModel;
        //serviceManager.setDisplayArea(null); // No usamos JTextArea, usamos tableModel
        initController();
    }

    private void initController() {
        serviceView.getRefreshButton().addActionListener(e -> {
            try {
                serviceManager.refreshServiceList(tableModel);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Error al listar servicios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}