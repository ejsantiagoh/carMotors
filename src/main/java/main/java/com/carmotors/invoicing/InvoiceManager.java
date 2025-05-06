/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Gestiona la lógica de negocio para facturas.
 * @author fashe
 */
public class InvoiceManager {
    private final InvoiceDAO invoiceDAO;

    public InvoiceManager() {
        invoiceDAO = new InvoiceDAO();
    }

    public void refreshInvoiceList(DefaultTableModel tableModel) {
        try {
            List<Invoice> invoiceList = invoiceDAO.listInvoices();
            tableModel.setRowCount(0); // Limpiar la tabla
            if (invoiceList.isEmpty()) {
                tableModel.addRow(new Object[]{"No hay facturas registradas.", "", "", "", "", "", "", ""});
            } else {
                for (Invoice invoice : invoiceList) {
                    tableModel.addRow(new Object[]{
                        invoice.getId(),
                        invoice.getCustomerId(),
                        invoice.getServiceId(),
                        invoice.getDate(),
                        invoice.getSubtotal(),
                        invoice.getTax(),
                        invoice.getTotal(),
                        invoice.getCufeCode()
                    });
                }
            }
        } catch (RuntimeException ex) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"Error al listar facturas: " + ex.getMessage(), "", "", "", "", "", "", ""});
        }
    }

    public Invoice getInvoiceByRow(int row, DefaultTableModel tableModel) {
        List<Invoice> invoiceList = invoiceDAO.listInvoices();
        if (row >= 0 && row < invoiceList.size()) {
            return invoiceList.get(row);
        }
        return null;
    }
}