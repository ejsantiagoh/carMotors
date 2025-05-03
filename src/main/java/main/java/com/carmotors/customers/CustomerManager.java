/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.customers;

import java.util.List;

/**
 *
 * @author fashe
 */


public class CustomerManager {
    private final CustomerDAO customerDAO;

    public CustomerManager() {
        this.customerDAO = new CustomerDAO();
    }

    public List<Customer> listCustomers() {
        return customerDAO.listCustomers();
    }
}
