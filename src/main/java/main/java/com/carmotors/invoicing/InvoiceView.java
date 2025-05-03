/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author fashe
 */


public class InvoiceView extends JPanel {
    private JTextArea invoiceList;
    private JButton refreshButton;

    public InvoiceView() {
        setLayout(new BorderLayout());

        invoiceList = new JTextArea(10, 50);
        invoiceList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(invoiceList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        refreshButton = new JButton("Actualizar Facturas");
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setInvoiceList(String invoices) {
        invoiceList.setText(invoices);
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}