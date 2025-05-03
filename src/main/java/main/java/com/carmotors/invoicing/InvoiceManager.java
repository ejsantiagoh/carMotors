/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
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
                tableModel.addRow(new Object[]{"No hay facturas registradas.", "", "", "", "", "", "", "", "", ""});
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
                        invoice.getCufeCode(),
                        invoice.getQrCodeUrl(),
                        invoice.getPdfUrl()
                    });
                }
            }
        } catch (RuntimeException ex) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"Error al listar facturas: " + ex.getMessage(), "", "", "", "", "", "", "", "", ""});
        }
    }
}