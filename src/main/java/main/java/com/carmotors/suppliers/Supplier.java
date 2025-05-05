/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.suppliers;

/*
 *
 * @author yorle
 */

// Supplier

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private int id; // ID único del proveedor
    private String name; // Nombre del proveedor
    private String nit; // NIT del proveedor
    private String contact; // Contacto del proveedor
    private String visitFrequency; // Frecuencia de visitas (ej. "Semanal", "Mensual")
    private List<SuppliedProduct> suppliedProducts; // Lista de productos suministrados (en memoria)
    private int punctualityScore; // Puntuación de puntualidad (1-5)
    private int qualityScore; // Puntuación de calidad (1-5)
    private int costScore; // Puntuación de costo (1-5)

    public Supplier(int id, String name, String nit, String contact, String visitFrequency) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.contact = contact;
        this.visitFrequency = visitFrequency;
        this.suppliedProducts = new ArrayList<>();
        this.punctualityScore = 0;
        this.qualityScore = 0;
        this.costScore = 0;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getVisitFrequency() { return visitFrequency; }
    public void setVisitFrequency(String visitFrequency) { this.visitFrequency = visitFrequency; }
    public List<SuppliedProduct> getSuppliedProducts() { return suppliedProducts; }
    public void addSuppliedProduct(SuppliedProduct product) { this.suppliedProducts.add(product); }
    public int getPunctualityScore() { return punctualityScore; }
    public void setPunctualityScore(int score) { this.punctualityScore = score; }
    public int getQualityScore() { return qualityScore; }
    public void setQualityScore(int score) { this.qualityScore = score; }
    public int getCostScore() { return costScore; }
    public void setCostScore(int score) { this.costScore = score; }

    public double getAverageScore() {
        return (punctualityScore + qualityScore + costScore) / 3.0;
    }
}