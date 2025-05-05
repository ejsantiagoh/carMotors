/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.customers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 *
 * @author fashe
 */

public class CustomerView extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public CustomerView() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nombre", "Identificación", "Teléfono", "Email", "Dirección"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        customerTable.setFillsViewportHeight(true);
        customerTable.setFont(new Font("Arial", Font.PLAIN, 12));
        customerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < customerTable.getColumnCount(); i++) {
            customerTable.getColumnModel().getColumn(i).setPreferredWidth(140);
        }
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        refreshButton = new JButton("Actualizar Clientes");
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