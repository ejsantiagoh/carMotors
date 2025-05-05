package main.java.com.carmotors.suppliers;

/*
 * @author yorle
 */

// Supplier Controller

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

public class SupplierController {
    private SupplierView view;
    private SupplierDAO dao;

    public SupplierController(SupplierView view, SupplierDAO dao) {
        this.view = view;
        this.dao = dao;
        if (this.view != null) {
            initListeners();
        }
    }

    private void initListeners() {
        view.getAddSupplierButton().addActionListener(e -> registerSupplier());
        view.getAddProductButton().addActionListener(e -> addSuppliedProduct());
        view.getEvaluateButton().addActionListener(e -> evaluateSupplier());
        view.getSupplierTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSupplierTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int supplierId = (int) view.getSupplierTable().getValueAt(selectedRow, 0);
                    try {
                        Supplier supplier = dao.getAllSuppliers().stream()
                                .filter(s -> s.getId() == supplierId)
                                .findFirst()
                                .orElse(null);
                        if (supplier != null) {
                            view.refreshProductTable(supplier);
                            view.updateAverageScore(supplier.getAverageScore());
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(view, "Error al cargar productos: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void registerSupplier() {
        try {
            String name = view.getNameField().getText();
            String nit = view.getNitField().getText();
            String contact = view.getContactField().getText();
            String visitFrequency = (String) view.getVisitFrequencyCombo().getSelectedItem();

            if (name.isEmpty() || nit.isEmpty() || contact.isEmpty() || visitFrequency == null) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            Supplier supplier = new Supplier(0, name, nit, contact, visitFrequency);
            dao.saveSupplier(supplier);
            view.refreshSupplierTable();
            clearSupplierFields();
            JOptionPane.showMessageDialog(view, "Proveedor registrado con éxito");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al registrar proveedor: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    private void addSuppliedProduct() {
        try {
            int selectedRow = view.getSupplierTable().getSelectedRow();
            if (selectedRow < 0) {
                int rowCount = view.getSupplierTable().getRowCount();
                if (rowCount > 0) {
                    view.getSupplierTable().setRowSelectionInterval(0, 0);
                    selectedRow = 0;
                } else {
                    throw new IllegalArgumentException("No hay proveedores registrados para añadir productos.");
                }
            }

            int supplierId = (int) view.getSupplierTable().getValueAt(selectedRow, 0);
            Supplier supplier = dao.getAllSuppliers().stream()
                    .filter(s -> s.getId() == supplierId)
                    .findFirst()
                    .orElse(null);
            if (supplier == null) {
                throw new IllegalArgumentException("Proveedor no encontrado.");
            }

            String type = view.getProductTypeField().getText();
            String quantityStr = view.getQuantityField().getText();
            String dateStr = view.getDateField().getText();

            if (type.isEmpty() || quantityStr.isEmpty() || dateStr.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos de producto son obligatorios.");
            }

            int quantity = Integer.parseInt(quantityStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date supplyDate = sdf.parse(dateStr);

            SuppliedProduct product = new SuppliedProduct(type, quantity, supplyDate);
            dao.saveSuppliedProduct(supplier, product);
            supplier.addSuppliedProduct(product);
            view.refreshProductTable(supplier);
            clearProductFields();
            JOptionPane.showMessageDialog(view, "Producto registrado con éxito");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "La cantidad debe ser un número válido.");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(view, "Formato de fecha inválido. Use dd/MM/yyyy.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al registrar producto: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    private void evaluateSupplier() {
        try {
            int selectedRow = view.getSupplierTable().getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Seleccione un proveedor para evaluar.");
            }

            int supplierId = (int) view.getSupplierTable().getValueAt(selectedRow, 0);
            Supplier supplier = dao.getAllSuppliers().stream()
                    .filter(s -> s.getId() == supplierId)
                    .findFirst()
                    .orElse(null);
            if (supplier == null) {
                throw new IllegalArgumentException("Proveedor no encontrado.");
            }

            // Obtener y validar las puntuaciones de los JTextField
            int punctuality = parseScore(view.getPunctualityField().getText(), "Puntualidad");
            int quality = parseScore(view.getQualityField().getText(), "Calidad");
            int cost = parseScore(view.getCostField().getText(), "Costo");

            // Asignar las puntuaciones al proveedor
            supplier.setPunctualityScore(punctuality);
            supplier.setQualityScore(quality);
            supplier.setCostScore(cost);
            dao.updateEvaluation(supplier);
            view.refreshSupplierTable();
            view.updateAverageScore(supplier.getAverageScore());
            JOptionPane.showMessageDialog(view, "Evaluación registrada con éxito");

            // Opcional: Limpiar los campos después de la evaluación
            view.getPunctualityField().setText("");
            view.getQualityField().setText("");
            view.getCostField().setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al evaluar proveedor: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, "Error de validación: " + ex.getMessage());
        }
    }

    private int parseScore(String scoreText, String fieldName) throws NumberFormatException, IllegalArgumentException {
        if (scoreText == null || scoreText.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + fieldName + " no puede estar vacío.");
        }
        try {
            int score = Integer.parseInt(scoreText.trim());
            if (score < 1 || score > 5) {
                throw new NumberFormatException("El valor de " + fieldName + " debe estar entre 1 y 5.");
            }
            return score;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Por favor, ingrese un número válido para " + fieldName + " (1-5): " + e.getMessage());
        }
    }

    private void clearSupplierFields() {
        view.getNameField().setText("");
        view.getNitField().setText("");
        view.getContactField().setText("");
        view.getVisitFrequencyCombo().setSelectedIndex(0);
    }

    private void clearProductFields() {
        view.getProductTypeField().setText("");
        view.getQuantityField().setText("");
        view.getDateField().setText("");
    }

    public void setView(SupplierView view) {
        this.view = view;
        if (this.view != null) {
            initListeners();
        }
    }
}