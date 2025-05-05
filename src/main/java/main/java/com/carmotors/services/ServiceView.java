/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class ServiceView extends JPanel {
    private JTable serviceTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public ServiceView() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Tipo", "Vehículo ID", "Descripción", "Tiempo Estimado (horas)", "Costo Labor ($)", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        serviceTable = new JTable(tableModel);
        serviceTable.setFillsViewportHeight(true);
        serviceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        serviceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        serviceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < serviceTable.getColumnCount(); i++) {
            serviceTable.getColumnModel().getColumn(i).setPreferredWidth(120);
        }
        JScrollPane scrollPane = new JScrollPane(serviceTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        refreshButton = new JButton("Actualizar Servicios");
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}