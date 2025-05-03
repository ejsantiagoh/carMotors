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
 *
 * @author fashe
 */


public class InvoiceDAO {
    public List<Invoice> listInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoice";
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
                invoice.setQrCodeUrl(rs.getString("qr_code_url"));
                invoice.setPdfUrl(rs.getString("pdf_url"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas: " + e.getMessage(), e);
        }
        return invoices;
    }
}