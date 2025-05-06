/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import javax.swing.*;

public class InvoiceController {
    private InvoiceView view;
    private InvoiceManager invoiceManager;

    public InvoiceController(InvoiceView view) {
        this.view = view;
        this.invoiceManager = new InvoiceManager();
        setupListeners();
    }
    

    private void setupListeners() {
        view.getRefreshButton().addActionListener(e -> refreshInvoices());
    }

    public void refreshInvoices() {
        invoiceManager.refreshInvoiceList(view.getTableModel());
    }
}