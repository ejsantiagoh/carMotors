/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fashe
 */

public class InvoiceController {
    private final InvoiceManager invoiceManager;
    private final InvoiceView invoiceView;
    private final DefaultTableModel tableModel;

    public InvoiceController(InvoiceManager invoiceManager, InvoiceView invoiceView, DefaultTableModel tableModel) {
        this.invoiceManager = invoiceManager;
        this.invoiceView = invoiceView;
        this.tableModel = tableModel;
        initController();
    }

    private void initController() {
        invoiceView.getRefreshButton().addActionListener(e -> {
            try {
                invoiceManager.refreshInvoiceList(tableModel);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Error al listar facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}