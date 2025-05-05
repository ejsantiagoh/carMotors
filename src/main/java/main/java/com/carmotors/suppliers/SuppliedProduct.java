package main.java.com.carmotors.suppliers;

import java.util.Date;

public class SuppliedProduct {
    private String type; // Tipo de repuesto (ej. "Mecánico", "Eléctrico")
    private int quantity; // Cantidad suministrada
    private Date supplyDate; // Fecha de suministro

    public SuppliedProduct(String type, int quantity, Date supplyDate) {
        this.type = type;
        this.quantity = quantity;
        this.supplyDate = supplyDate;
    }

    // Getters y setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Date getSupplyDate() { return supplyDate; }
    public void setSupplyDate(Date supplyDate) { this.supplyDate = supplyDate; }
}


