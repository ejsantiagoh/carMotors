/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.customers;

import java.util.List;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author fashe
 */

public class CustomerManager {
    private final CustomerDAO customerDAO;

    public CustomerManager() {
        customerDAO = new CustomerDAO();
    }

    public void refreshCustomerList(DefaultTableModel tableModel) {
        try {
            List<Customer> customers = customerDAO.listCustomers();
            tableModel.setRowCount(0); // Limpiar la tabla
            if (customers.isEmpty()) {
                tableModel.addRow(new Object[]{"No hay clientes registrados.", "", "", "", "", ""});
            } else {
                for (Customer customer : customers) {
                    tableModel.addRow(new Object[]{
                        customer.getId(),
                        customer.getName(),
                        customer.getIdentification(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getAddressId()
                    });
                }
            }
        } catch (RuntimeException ex) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"Error al listar clientes: " + ex.getMessage(), "", "", "", "", ""});
        }
    }
}