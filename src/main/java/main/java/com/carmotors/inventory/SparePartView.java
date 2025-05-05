package main.java.com.carmotors.inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class SparePartView extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, brandField, modelField, supplierIdField;
    private JTextField stockQuantityField, minStockLevelField, estimatedLifespanField, batchIdField;
    private JComboBox<String> typeCombo;
    private JComboBox<String> statusCombo;
    private JButton addButton;
    private SparePartController controller;
    private SparePartDAO dao;
    private JFrame parentFrame; // Cambiar a JFrame

    public SparePartView(JFrame parentFrame) { // Aceptar JFrame
        this.parentFrame = parentFrame;
        dao = new SparePartDAO();
        initializeUI();
        controller = new SparePartController(this, dao);
    }

    private void initializeUI() {
        setTitle("Gestión de Inventarios de Repuestos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal con fondo claro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 242, 245));

        // Panel de entrada de datos con estilo moderno
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Información del Repuesto
        JLabel sectionLabel1 = new JLabel("Información del Repuesto");
        sectionLabel1.setFont(new Font("Arial", Font.BOLD, 14));
        sectionLabel1.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(sectionLabel1, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Nombre del Repuesto:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 12));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel typeLabel = new JLabel("Tipo (Mecánico, Eléctrico, Carrocería, Consumo):");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        String[] typeOptions = {"Mecánico", "Eléctrico", "Carrocería", "Consumo"};
        typeCombo = new JComboBox<>(typeOptions);
        typeCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        typeCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(typeCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel brandLabel = new JLabel("Marca:");
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(brandLabel, gbc);
        gbc.gridx = 1;
        brandField = new JTextField(20);
        brandField.setFont(new Font("Arial", Font.PLAIN, 12));
        brandField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(brandField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel modelLabel = new JLabel("Modelo:");
        modelLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(modelLabel, gbc);
        gbc.gridx = 1;
        modelField = new JTextField(20);
        modelField.setFont(new Font("Arial", Font.PLAIN, 12));
        modelField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(modelField, gbc);

        // Información del Proveedor
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel sectionLabel2 = new JLabel("Información del Proveedor");
        sectionLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        sectionLabel2.setForeground(new Color(25, 118, 210));
        gbc.gridwidth = 2;
        inputPanel.add(sectionLabel2, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel supplierIdLabel = new JLabel("ID Proveedor:");
        supplierIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(supplierIdLabel, gbc);
        gbc.gridx = 1;
        supplierIdField = new JTextField(20);
        supplierIdField.setFont(new Font("Arial", Font.PLAIN, 12));
        supplierIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(supplierIdField, gbc);

        // Gestión de Stock
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel sectionLabel3 = new JLabel("Gestión de Stock");
        sectionLabel3.setFont(new Font("Arial", Font.BOLD, 14));
        sectionLabel3.setForeground(new Color(25, 118, 210));
        gbc.gridwidth = 2;
        inputPanel.add(sectionLabel3, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel stockQuantityLabel = new JLabel("Cantidad en Stock:");
        stockQuantityLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(stockQuantityLabel, gbc);
        gbc.gridx = 1;
        stockQuantityField = new JTextField(20);
        stockQuantityField.setFont(new Font("Arial", Font.PLAIN, 12));
        stockQuantityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(stockQuantityField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel minStockLevelLabel = new JLabel("Nivel Mínimo de Stock:");
        minStockLevelLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(minStockLevelLabel, gbc);
        gbc.gridx = 1;
        minStockLevelField = new JTextField(20);
        minStockLevelField.setFont(new Font("Arial", Font.PLAIN, 12));
        minStockLevelField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(minStockLevelField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel estimatedLifespanLabel = new JLabel("Vida Útil Estimada (días):");
        estimatedLifespanLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(estimatedLifespanLabel, gbc);
        gbc.gridx = 1;
        estimatedLifespanField = new JTextField(20);
        estimatedLifespanField.setFont(new Font("Arial", Font.PLAIN, 12));
        estimatedLifespanField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(estimatedLifespanField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel batchIdLabel = new JLabel("ID Lote:");
        batchIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(batchIdLabel, gbc);
        gbc.gridx = 1;
        batchIdField = new JTextField(20);
        batchIdField.setFont(new Font("Arial", Font.PLAIN, 12));
        batchIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(batchIdField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel statusLabel = new JLabel("Estado:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        statusCombo = new JComboBox<>(new String[]{"Disponible", "Reservado", "Fuera de servicio"});
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        statusCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(statusCombo, gbc);

        // Botón de registro con diseño moderno
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        addButton = new JButton("Registrar Repuesto");
        addButton.setBackground(new Color(25, 118, 210));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setFocusPainted(false);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        inputPanel.add(addButton, gbc);

        // Envolver inputPanel en un JScrollPane para permitir desplazamiento
        JScrollPane inputScrollPane = new JScrollPane(inputPanel);
        inputScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                        "Registrar Repuesto",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 16),
                        new Color(25, 118, 210)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setPreferredSize(new Dimension(0, 300));

        // Tabla
        String[] columnNames = {"Nombre", "Tipo", "Marca", "Modelo", "ID Proveedor", "Stock", "Nivel Mínimo", "Vida Útil", "Estado", "Lote"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(25, 118, 210));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                "Inventario Actual",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16),
                new Color(25, 118, 210)
        ));
        tableScrollPane.setPreferredSize(new Dimension(0, 300));

        // Botón Regresar
        JButton backButton = new JButton("Regresar");
        backButton.setBackground(new Color(25, 118, 210));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true); // Mostrar la ventana principal
            dispose(); // Cerrar esta ventana
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(backButton);
        buttonPanel.setPreferredSize(new Dimension(0, 60));

        // Agregar componentes al panel principal
        mainPanel.add(inputScrollPane, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Inicializar vista
        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        try {
            for (SparePart sp : dao.getAllSpareParts()) {
                tableModel.addRow(new Object[]{
                        sp.getName(),
                        sp.getType(),
                        sp.getBrand(),
                        sp.getModel(),
                        sp.getSupplierId(),
                        sp.getStockQuantity(),
                        sp.getMinStockLevel(),
                        sp.getEstimatedLifespan(),
                        sp.getStatus(),
                        sp.getBatchId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla: " + e.getMessage());
        }
    }

    public JTextField getNameField() { return nameField; }
    public JComboBox<String> getTypeCombo() { return typeCombo; }
    public JTextField getBrandField() { return brandField; }
    public JTextField getModelField() { return modelField; }
    public JTextField getSupplierIdField() { return supplierIdField; }
    public JTextField getStockQuantityField() { return stockQuantityField; }
    public JTextField getMinStockLevelField() { return minStockLevelField; }
    public JTextField getEstimatedLifespanField() { return estimatedLifespanField; }
    public JTextField getBatchIdField() { return batchIdField; }
    public JComboBox<String> getStatusCombo() { return statusCombo; }
    public JButton getAddButton() { return addButton; }
}