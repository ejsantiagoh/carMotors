/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import main.java.com.carmotors.customers.CustomerView;
import main.java.com.carmotors.invoicing.InvoiceView;
import main.java.com.carmotors.services.ServiceView;

/**
 *
 * @author fashe
 */

public class ReportView extends JDialog {
    private final DefaultTableModel tableModel;

    public ReportView(JFrame parent, CustomerView customerView, InvoiceView invoiceView, ServiceView serviceView) {
        super(parent, "Reportes y Estadísticas", true);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(parent);

        String[] columnNames = {"Reporte", "Valor"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Calcular reportes
        int customerCount = customerView.getTableModel().getRowCount() > 0 ? customerView.getTableModel().getRowCount() : 0;
        double totalRevenue = calculateTotalRevenue(invoiceView.getTableModel());
        int serviceCount = serviceView.getTableModel().getRowCount() > 0 ? serviceView.getTableModel().getRowCount() : 0;

        tableModel.addRow(new Object[]{"Total Clientes", customerCount});
        tableModel.addRow(new Object[]{"Ingresos Totales ($)", new DecimalFormat("#,##0.00").format(totalRevenue)});
        tableModel.addRow(new Object[]{"Total Servicios", serviceCount});

        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 12));
        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < reportTable.getColumnCount(); i++) {
            reportTable.getColumnModel().getColumn(i).setPreferredWidth(300);
        }

        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private double calculateTotalRevenue(DefaultTableModel invoiceModel) {
        double total = 0.0;
        for (int i = 0; i < invoiceModel.getRowCount(); i++) {
            try {
                Object value = invoiceModel.getValueAt(i, 6); // Columna "Total ($)"
                if (value instanceof Number) {
                    total += ((Number) value).doubleValue();
                }
            } catch (Exception e) {
                // Ignorar filas con datos inválidos
            }
        }
        return total;
    }
}
