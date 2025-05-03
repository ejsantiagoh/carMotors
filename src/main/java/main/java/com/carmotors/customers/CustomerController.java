/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.customers;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fashe
 */


public class CustomerController {
    private final CustomerManager customerManager;
    private final CustomerView customerView;
    private final DefaultTableModel tableModel;

    public CustomerController(CustomerManager customerManager, CustomerView customerView, DefaultTableModel tableModel) {
        this.customerManager = customerManager;
        this.customerView = customerView;
        this.tableModel = tableModel;
        initController();
    }

    private void initController() {
        customerView.getRefreshButton().addActionListener(e -> {
            try {
                customerManager.refreshCustomerList(tableModel);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Error al listar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}