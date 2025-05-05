package main.java.com.carmotors.inventory;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class SparePartController {
    private SparePartView view;
    private SparePartDAO dao;

    public SparePartController(SparePartView view, SparePartDAO dao) {
        this.view = view;
        this.dao = dao;
        if (view != null) { // Verificar si la vista no es null antes de añadir listeners
            initListeners();
        }
    }

    private void initListeners() {
        view.getAddButton().addActionListener(e -> registerSparePart());
    }

    private void registerSparePart() {
        try {
            String type = (String) view.getTypeCombo().getSelectedItem();
            if (type == null) {
                throw new IllegalArgumentException("Debe seleccionar un tipo.");
            }

            SparePart sparePart = new SparePart(
                    view.getNameField().getText(),
                    type,
                    view.getBrandField().getText(),
                    view.getModelField().getText(),
                    Integer.parseInt(view.getSupplierIdField().getText()),
                    null,
                    null,
                    null,
                    Integer.parseInt(view.getStockQuantityField().getText()),
                    Integer.parseInt(view.getMinStockLevelField().getText()),
                    null,
                    Integer.parseInt(view.getEstimatedLifespanField().getText()),
                    (String) view.getStatusCombo().getSelectedItem(),
                    Integer.parseInt(view.getBatchIdField().getText())
            );
            dao.saveSparePart(sparePart);
            view.refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(view, "Repuesto registrado con éxito");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Por favor, ingrese valores numéricos válidos para ID Proveedor, Stock, Nivel Mínimo, Vida Útil o ID Lote.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al registrar el repuesto: " + ex.getMessage());
        }
    }

    private void clearFields() {
        view.getNameField().setText("");
        view.getTypeCombo().setSelectedIndex(0);
        view.getBrandField().setText("");
        view.getModelField().setText("");
        view.getSupplierIdField().setText("");
        view.getStockQuantityField().setText("");
        view.getMinStockLevelField().setText("");
        view.getEstimatedLifespanField().setText("");
        view.getBatchIdField().setText("");
        view.getStatusCombo().setSelectedIndex(0);
    }

    // Método para actualizar la vista
    public void setView(SparePartView view) {
        this.view = view;
        initListeners();
    }
}