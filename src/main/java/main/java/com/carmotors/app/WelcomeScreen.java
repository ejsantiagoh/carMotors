package main.java.com.carmotors.app;

import main.java.com.carmotors.customers.CustomerController;
import main.java.com.carmotors.customers.CustomerManager;
import main.java.com.carmotors.customers.CustomerView;
import main.java.com.carmotors.invoicing.InvoiceController;
import main.java.com.carmotors.invoicing.InvoiceManager;
import main.java.com.carmotors.invoicing.InvoiceView;
import main.java.com.carmotors.services.ServiceController;
import main.java.com.carmotors.services.ServiceManager;
import main.java.com.carmotors.services.ServiceView;
import main.java.com.carmotors.inventory.SparePartView;
import main.java.com.carmotors.inventory.SparePartController;
import main.java.com.carmotors.inventory.SparePartDAO;
import main.java.com.carmotors.suppliers.SupplierView;
import main.java.com.carmotors.suppliers.SupplierController;
import main.java.com.carmotors.suppliers.SupplierDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WelcomeScreen {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ServiceView serviceView;
    private CustomerView customerView;
    private InvoiceView invoiceView;
    private SparePartView inventoryView;
    private SupplierView supplierView;
    private CustomerManager customerManager;
    private InvoiceManager invoiceManager;
    private ServiceManager serviceManager;
    private CustomerController customerController;
    private InvoiceController invoiceController;
    private ServiceController serviceController;
    private SparePartController sparePartController;
    private SupplierController supplierController;
    private SparePartDAO sparePartDAO;
    private SupplierDAO supplierDAO;

    public WelcomeScreen() {
        frame = new JFrame("CarMotors - Gestión de Servicios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Inicializar managers, vistas y DAO
        serviceView = new ServiceView();
        customerView = new CustomerView();
        invoiceView = new InvoiceView();
        inventoryView = new SparePartView();
        supplierView = new SupplierView();
        customerManager = new CustomerManager();
        invoiceManager = new InvoiceManager();
        serviceManager = new ServiceManager();
        sparePartDAO = new SparePartDAO();
        supplierDAO = new SupplierDAO();

        // Inicializar controladores
        serviceController = new ServiceController(serviceManager, serviceView, new DefaultTableModel());
        customerController = new CustomerController(customerManager, customerView, new DefaultTableModel());
        invoiceController = new InvoiceController(invoiceManager, invoiceView, new DefaultTableModel());
        sparePartController = new SparePartController(inventoryView, sparePartDAO); // Inicializar con DAO
        supplierController = new SupplierController(supplierView, supplierDAO);    // Inicializar con DAO

        // Pantalla de bienvenida
        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "welcome");

        // Añadir paneles
        JPanel servicePanel = createServicePanel();
        cardPanel.add(servicePanel, "services");

        JPanel customerPanel = createCustomerPanel();
        cardPanel.add(customerPanel, "customers");

        JPanel invoicePanel = createInvoicePanel();
        cardPanel.add(invoicePanel, "invoices");

        JPanel inventoryPanel = createInventoryPanel();
        cardPanel.add(inventoryPanel, "inventory");

        JPanel suppliersPanel = createSuppliersPanel();
        cardPanel.add(suppliersPanel, "suppliers");

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 120, 215), 0, getHeight(), new Color(0, 60, 120));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Bienvenido a CarMotors", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel("<html><center>Gestión de Servicios Automotrices<br>Seleccione una sección para comenzar</center></html>", SwingConstants.CENTER);
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(descriptionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 20));
        buttonPanel.setOpaque(false);

        JButton servicesButton = createStyledButton("Gestión de Servicios");
        JButton customersButton = createStyledButton("Gestión de Clientes");
        JButton invoicesButton = createStyledButton("Gestión de Facturas");
        JButton inventoryButton = createStyledButton("Gestión de Inventario");
        JButton suppliersButton = createStyledButton("Gestión de Proveedores");

        servicesButton.addActionListener(e -> cardLayout.show(cardPanel, "services"));
        customersButton.addActionListener(e -> cardLayout.show(cardPanel, "customers"));
        invoicesButton.addActionListener(e -> cardLayout.show(cardPanel, "invoices"));
        inventoryButton.addActionListener(e -> {
            inventoryView = new SparePartView();
            inventoryView.setVisible(true);
            cardLayout.show(cardPanel, "inventory");
        });
        suppliersButton.addActionListener(e -> {
            supplierView = new SupplierView();
            supplierView.setVisible(true);
            cardLayout.show(cardPanel, "suppliers");
        });

        buttonPanel.add(servicesButton);
        buttonPanel.add(customersButton);
        buttonPanel.add(invoicesButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(suppliersButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createServicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 240, 240));

        JLabel title = new JLabel("Gestión de Servicios", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Tipo", "Vehículo ID", "Descripción", "Tiempo Estimado (horas)", "Costo Labor ($)", "Estado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        serviceController = new ServiceController(serviceManager, serviceView, tableModel);

        JButton backButton = createStyledButton("Regresar");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (serviceView != null && serviceView.getRefreshButton() != null) {
            buttonPanel.add(serviceView.getRefreshButton());
        } else {
            JButton placeholder = new JButton("Actualizar Servicios (Error)");
            placeholder.setEnabled(false);
            buttonPanel.add(placeholder);
        }
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nombre", "Identificación", "Teléfono", "Email", "Dirección"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        customerController = new CustomerController(customerManager, customerView, tableModel);

        JButton backButton = createStyledButton("Regresar");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (customerView != null && customerView.getRefreshButton() != null) {
            buttonPanel.add(customerView.getRefreshButton());
        } else {
            JButton placeholder = new JButton("Actualizar Clientes (Error)");
            placeholder.setEnabled(false);
            buttonPanel.add(placeholder);
        }
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 255));

        JLabel title = new JLabel("Gestión de Facturas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Cliente ID", "Servicio ID", "Fecha", "Subtotal ($)", "Impuesto ($)", "Total ($)", "CUFE", "QR", "PDF"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        invoiceController = new InvoiceController(invoiceManager, invoiceView, tableModel);

        JButton backButton = createStyledButton("Regresar");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (invoiceView != null && invoiceView.getRefreshButton() != null) {
            buttonPanel.add(invoiceView.getRefreshButton());
        } else {
            JButton placeholder = new JButton("Actualizar Facturas (Error)");
            placeholder.setEnabled(false);
            buttonPanel.add(placeholder);
        }
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 240));

        JLabel title = new JLabel("Gestión de Inventario", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        // Envolver el JFrame en un JPanel para integrarlo al CardLayout
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        inventoryView.setVisible(true);
        inventoryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Evitar que cierre la aplicación
        wrapperPanel.add(inventoryView.getContentPane(), BorderLayout.CENTER);

        panel.add(wrapperPanel, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Regresar");
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "welcome");
            inventoryView.dispose(); // Liberar recursos al volver
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSuppliersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 255));

        JLabel title = new JLabel("Gestión de Proveedores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        // Envolver el JFrame en un JPanel para integrarlo al CardLayout
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        supplierView.setVisible(true);
        supplierView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Evitar que cierre la aplicación
        wrapperPanel.add(supplierView.getContentPane(), BorderLayout.CENTER);

        panel.add(wrapperPanel, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Regresar");
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "welcome");
            supplierView.dispose(); // Liberar recursos al volver
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeScreen::new);
    }
}