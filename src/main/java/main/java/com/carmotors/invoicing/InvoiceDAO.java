/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.invoicing;

import main.java.com.carmotors.common.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar operaciones de facturas en la base de datos.
 * @author fashe
 */
public class InvoiceDAO {
    public List<Invoice> listInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.id_invoice, i.customer_id, i.service_id, i.date, i.subtotal, i.tax, i.total, i.cufe_code, " +
                    "c.name AS customer_name, c.identification AS customer_document, c.address_id AS customer_address_id " +
                    "FROM invoice i " +
                    "JOIN customer c ON i.customer_id = c.id_customer";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id_invoice"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setServiceId(rs.getInt("service_id"));
                invoice.setDate(rs.getDate("date"));
                invoice.setSubtotal(rs.getDouble("subtotal"));
                invoice.setTax(rs.getDouble("tax"));
                invoice.setTotal(rs.getDouble("total"));
                invoice.setCufeCode(rs.getString("cufe_code"));
                // Añadir información del cliente
                invoice.setCustomerName(rs.getString("customer_name"));
                invoice.setCustomerDocument(rs.getString("customer_document"));
                invoice.setCustomerAddressId(rs.getInt("customer_address_id"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas: " + e.getMessage(), e);
        }
        return invoices;
    }
}