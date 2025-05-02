/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import java.util.List;
/**
 *
 * @author fashe
 */




public class InvoiceManager {
    private final InvoiceDAO invoiceDAO;

    public InvoiceManager() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Invoice> listInvoices() {
        return invoiceDAO.listInvoices();
    }
}
