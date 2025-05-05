/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 *
 * @author fashe
 */

public class InvoiceView extends JPanel {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public InvoiceView() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Cliente ID", "Servicio ID", "Fecha", "Subtotal ($)", "Impuesto ($)", "Total ($)", "CUFE", "QR", "PDF"};
        tableModel = new DefaultTableModel(columnNames, 0);
        invoiceTable = new JTable(tableModel);
        invoiceTable.setFillsViewportHeight(true);
        invoiceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        invoiceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        invoiceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < invoiceTable.getColumnCount(); i++) {
            invoiceTable.getColumnModel().getColumn(i).setPreferredWidth(90);
        }
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        refreshButton = new JButton("Actualizar Facturas");
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