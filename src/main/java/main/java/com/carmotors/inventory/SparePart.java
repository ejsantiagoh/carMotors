/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.inventory;

/*
 *
 * @author yorle
 */

// SparePart
import java.util.Date;

public class SparePart {
    private String name;
    private String type;
    private String brand;
    private String model;
    private int supplierId;
    private String supplierName;
    private String contactInfo;
    private String supplyFrequency;
    private int stockQuantity;
    private int minStockLevel;
    private Date entryDate;
    private int estimatedLifespan;
    private String status;
    private int batchId;

    // Constructor
    public SparePart(String name, String type, String brand, String model, int supplierId, String supplierName,
                     String contactInfo, String supplyFrequency, int stockQuantity, int minStockLevel,
                     Date entryDate, int estimatedLifespan, String status, int batchId) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.supplyFrequency = supplyFrequency;
        this.stockQuantity = stockQuantity;
        this.minStockLevel = minStockLevel;
        this.entryDate = entryDate;
        this.estimatedLifespan = estimatedLifespan;
        this.status = status;
        this.batchId = batchId;
    }

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getSupplyFrequency() { return supplyFrequency; }
    public void setSupplyFrequency(String supplyFrequency) { this.supplyFrequency = supplyFrequency; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public int getMinStockLevel() { return minStockLevel; }
    public void setMinStockLevel(int minStockLevel) { this.minStockLevel = minStockLevel; }
    public Date getEntryDate() { return entryDate; }
    public void setEntryDate(Date entryDate) { this.entryDate = entryDate; }
    public int getEstimatedLifespan() { return estimatedLifespan; }
    public void setEstimatedLifespan(int estimatedLifespan) { this.estimatedLifespan = estimatedLifespan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getBatchId() { return batchId; }
    public void setBatchId(int batchId) { this.batchId = batchId; }
}
