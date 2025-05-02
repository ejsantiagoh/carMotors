/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;

import main.java.com.carmotors.customers.CustomerManager;
import main.java.com.carmotors.invoicing.InvoiceManager;
import java.util.List;
import javax.swing.JOptionPane;
import main.java.com.carmotors.customers.Customer;
import main.java.com.carmotors.invoicing.Invoice;

/**
 *
 * @author fashe
 */


public class ServiceController {
    private final ServiceManager serviceManager;
    private final CustomerManager customerManager;
    private final InvoiceManager invoiceManager;
    private final ServiceView serviceView;

    public ServiceController(ServiceManager serviceManager, CustomerManager customerManager, InvoiceManager invoiceManager, ServiceView serviceView) {
        this.serviceManager = serviceManager;
        this.customerManager = customerManager;
        this.invoiceManager = invoiceManager;
        this.serviceView = serviceView;
        initController();
    }

    private void initController() {
        serviceView.getListServiceButton().addActionListener(e -> {
            try {
                List<Service> services = serviceManager.listServices();
                serviceView.displayServices(services);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(serviceView, "Error al listar servicios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        serviceView.getListCustomerButton().addActionListener(e -> {
            try {
                List<Customer> customers = customerManager.listCustomers();
                serviceView.displayCustomers(customers);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(serviceView, "Error al listar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        serviceView.getListInvoiceButton().addActionListener(e -> {
            try {
                List<Invoice> invoices = invoiceManager.listInvoices();
                serviceView.displayInvoices(invoices);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(serviceView, "Error al listar facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}