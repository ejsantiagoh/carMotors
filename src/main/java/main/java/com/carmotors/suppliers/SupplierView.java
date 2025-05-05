package main.java.com.carmotors.suppliers;

/*
 * @author yorle
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

public class SupplierView extends JFrame {
    private JFrame parentFrame;
    private DefaultTableModel supplierTableModel;
    private DefaultTableModel productTableModel;
    private JTable supplierTable;
    private JTable productTable;
    private JTextField nameField, nitField, contactField, productTypeField, quantityField, dateField;
    private JComboBox<String> visitFrequencyCombo;
    private JTextField punctualityField, qualityField, costField;
    private JButton addSupplierButton, addProductButton, evaluateButton;
    private SupplierController controller;
    private SupplierDAO dao;
    private JLabel averageScoreLabel;
    private JPanel combinedInputPanel;
    private JPanel buttonPanel;

    public SupplierView(JFrame parentFrame, SupplierController controller) {
        this.parentFrame = parentFrame;
        this.controller = controller;
        this.dao = new SupplierDAO();
        initializeUI();
    }

    private void initializeUI() {
        setupFrame();
        setupMainPanel();
        setupInputPanels();
        setupTables();
        setupButtonPanel();
        addComponents();
        refreshSupplierTable();
        pack(); // Ajusta el tamaño del frame al contenido
    }

    private void setupFrame() {
        setTitle("Gestión de Proveedores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 700)); // Tamaño mínimo para asegurar visibilidad
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setupMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5)); // Reduje el espaciado
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Reduje los márgenes
        mainPanel.setBackground(new Color(240, 242, 245));

        JLabel titleLabel = new JLabel("Gestión de Proveedores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Reduje el tamaño de la fuente
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupInputPanels() {
        JPanel supplierInputPanel = createSupplierInputPanel();
        JPanel productInputPanel = createProductInputPanel();
        JPanel evaluationPanel = createEvaluationPanel();

        combinedInputPanel = new JPanel();
        combinedInputPanel.setLayout(new BoxLayout(combinedInputPanel, BoxLayout.Y_AXIS));
        combinedInputPanel.setBackground(Color.WHITE);
        combinedInputPanel.add(supplierInputPanel);
        combinedInputPanel.add(Box.createVerticalStrut(5)); // Reduje el espacio vertical
        combinedInputPanel.add(productInputPanel);
        combinedInputPanel.add(Box.createVerticalStrut(5));
        combinedInputPanel.add(evaluationPanel);
    }

    private JPanel createSupplierInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = setupGBC();

        addSectionLabel(panel, "Registrar Proveedor", gbc);
        addTextField(panel, gbc, "Nombre:", nameField = new JTextField(15)); // Reduje el tamaño del campo
        addTextField(panel, gbc, "NIT:", nitField = new JTextField(15));
        addTextField(panel, gbc, "Contacto:", contactField = new JTextField(15));
        addComboBox(panel, gbc, "Frecuencia de Visitas:", visitFrequencyCombo = new JComboBox<>(new String[]{"Diaria", "Semanal", "Mensual", "Anual"}));
        addButton(panel, gbc, addSupplierButton = new JButton("Registrar Proveedor"));

        return panel;
    }

    private JPanel createProductInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = setupGBC();

        addSectionLabel(panel, "Registrar Productos Suministrados", gbc);
        addTextField(panel, gbc, "Tipo de Repuesto:", productTypeField = new JTextField(15));
        addTextField(panel, gbc, "Cantidad:", quantityField = new JTextField(15));
        addTextField(panel, gbc, "Fecha (dd/MM/yyyy):", dateField = new JTextField(15));
        addButton(panel, gbc, addProductButton = new JButton("Añadir Producto Suministrado"));

        return panel;
    }

    private JPanel createEvaluationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = setupGBC();

        addSectionLabel(panel, "Evaluar Proveedor", gbc);
        addTextField(panel, gbc, "Puntualidad (1-5):", punctualityField = new JTextField(5));
        addTextField(panel, gbc, "Calidad (1-5):", qualityField = new JTextField(5));
        addTextField(panel, gbc, "Costo (1-5):", costField = new JTextField(5));
        addButton(panel, gbc, evaluateButton = new JButton("Evaluar Proveedor"));

        return panel;
    }

    private GridBagConstraints setupGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5); // Reduje los márgenes internos
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addSectionLabel(JPanel panel, String text, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12)); // Reduje el tamaño de la fuente
        label.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
    }

    private void addTextField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        textField.setFont(new Font("Arial", Font.PLAIN, 10)); // Reduje el tamaño de la fuente
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(3, 3, 3, 3))); // Reduje el padding
        panel.add(textField, gbc);
        gbc.gridy++;
    }

    private void addComboBox(JPanel panel, GridBagConstraints gbc, String labelText, JComboBox<String> comboBox) {
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        comboBox.setFont(new Font("Arial", Font.PLAIN, 10)); // Reduje el tamaño de la fuente
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        panel.add(comboBox, gbc);
        gbc.gridy++;
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, JButton button) {
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        styleButton(button);
        panel.add(button, gbc);
    }

    private void setupTables() {
        supplierTableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "NIT", "Contacto", "Frecuencia de Visitas", "Puntuación Promedio"}, 0);
        supplierTable = new JTable(supplierTableModel);
        setupTableStyle(supplierTable, "Proveedores Registrados");

        productTableModel = new DefaultTableModel(new String[]{"Tipo de Repuesto", "Cantidad", "Fecha de Suministro"}, 0);
        productTable = new JTable(productTableModel);
        setupTableStyle(productTable, "Productos Suministrados");
    }

    private void setupTableStyle(JTable table, String title) {
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 10)); // Reduje el tamaño de la fuente
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
        table.getTableHeader().setBackground(new Color(25, 118, 210));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12), // Reduje el tamaño de la fuente
                new Color(25, 118, 210)));
        scrollPane.setPreferredSize(new Dimension(0, 120)); // Reduje la altura de las tablas
    }

    private void setupButtonPanel() {
        JButton backButton = new JButton("Regresar");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Reduje los márgenes
        buttonPanel.add(backButton);
        buttonPanel.setPreferredSize(new Dimension(0, 40)); // Reduje la altura
    }

    private void addComponents() {
        JPanel mainPanel = (JPanel) getContentPane().getComponent(0);
        mainPanel.add(combinedInputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(productTable), BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Reduje el tamaño de la fuente
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Reduje el padding
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void refreshSupplierTable() {
        supplierTableModel.setRowCount(0);
        try {
            for (Supplier supplier : dao.getAllSuppliers()) {
                supplierTableModel.addRow(new Object[]{
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getNit(),
                        supplier.getContact(),
                        supplier.getVisitFrequency(),
                        String.format("%.2f", supplier.getAverageScore())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla: " + e.getMessage());
        }
    }

    public void refreshProductTable(Supplier supplier) {
        productTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (SuppliedProduct product : supplier.getSuppliedProducts()) {
            productTableModel.addRow(new Object[]{
                    product.getType(),
                    product.getQuantity(),
                    sdf.format(product.getSupplyDate())
            });
        }
    }

    public void updateAverageScore(double score) {
        averageScoreLabel.setText("Puntuación Promedio: " + String.format("%.2f", score));
    }

    // Getters
    public JTextField getNameField() { return nameField; }
    public JTextField getNitField() { return nitField; }
    public JTextField getContactField() { return contactField; }
    public JComboBox<String> getVisitFrequencyCombo() { return visitFrequencyCombo; }
    public JTextField getProductTypeField() { return productTypeField; }
    public JTextField getQuantityField() { return quantityField; }
    public JTextField getDateField() { return dateField; }
    public JTextField getPunctualityField() { return punctualityField; }
    public JTextField getQualityField() { return qualityField; }
    public JTextField getCostField() { return costField; }
    public JButton getAddSupplierButton() { return addSupplierButton; }
    public JButton getAddProductButton() { return addProductButton; }
    public JButton getEvaluateButton() { return evaluateButton; }
    public JTable getSupplierTable() { return supplierTable; }
}