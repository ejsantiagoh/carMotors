/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import main.java.com.carmotors.customers.Customer;
import main.java.com.carmotors.invoicing.Invoice;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author fashe
 */

public class ServiceView extends JPanel {
    private JButton listServiceButton;
    private JButton listCustomerButton;
    private JButton listInvoiceButton;
    private JTextArea serviceTextArea;
    private JTextArea customerTextArea;
    private JTextArea invoiceTextArea;

    public ServiceView() {
        setLayout(new BorderLayout());

        // Panel superior con botones
        JPanel topPanel = new JPanel();
        listServiceButton = new JButton("Listar Servicios");
        listCustomerButton = new JButton("Listar Clientes");
        listInvoiceButton = new JButton("Listar Facturas");
        topPanel.add(listServiceButton);
        topPanel.add(listCustomerButton);
        topPanel.add(listInvoiceButton);
        add(topPanel, BorderLayout.NORTH);

        // Área de texto para servicios
        serviceTextArea = new JTextArea();
        serviceTextArea.setEditable(false);
        add(new JScrollPane(serviceTextArea), BorderLayout.CENTER);

        // Área de texto para clientes
        customerTextArea = new JTextArea();
        customerTextArea.setEditable(false);
        add(new JScrollPane(customerTextArea), BorderLayout.SOUTH);

        // Área de texto para facturas
        invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);
        add(new JScrollPane(invoiceTextArea), BorderLayout.EAST);
    }

    public JButton getListServiceButton() { return listServiceButton; }
    public JButton getListCustomerButton() { return listCustomerButton; }
    public JButton getListInvoiceButton() { return listInvoiceButton; }

    public void displayServices(List<Service> services) {
        serviceTextArea.setText("");
        for (Service service : services) {
            serviceTextArea.append("ID: " + service.getId() + ", Tipo: " + service.getType() + ", Estado: " + service.getStatus() + "\n");
        }
    }

    public void displayCustomers(List<Customer> customers) {
        customerTextArea.setText("");
        for (Customer customer : customers) {
            customerTextArea.append("ID: " + customer.getId() + ", Nombre: " + customer.getName() + "\n");
        }
    }

    public void displayInvoices(List<Invoice> invoices) {
        invoiceTextArea.setText("");
        for (Invoice invoice : invoices) {
            invoiceTextArea.append("ID: " + invoice.getId() + ", Cliente ID: " + invoice.getCustomerId() + ", Servicio ID: " + invoice.getServiceId() + 
                                  ", Fecha: " + invoice.getDate() + ", Subtotal: " + invoice.getSubtotal() + ", Tax: " + invoice.getTax() + 
                                  ", Total: " + invoice.getTotal() + ", CUFE: " + invoice.getCufeCode() + "\n");
        }
    }
}