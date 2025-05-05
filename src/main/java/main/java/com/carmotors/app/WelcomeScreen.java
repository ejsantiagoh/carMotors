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
import javax.swing.border.AbstractBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import static javax.swing.SwingConstants.*;

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
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setMaximumSize(new Dimension(1200, 800));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        serviceView = new ServiceView();
        customerView = new CustomerView();
        invoiceView = new InvoiceView();
        customerManager = new CustomerManager();
        invoiceManager = new InvoiceManager();
        serviceManager = new ServiceManager();
        sparePartDAO = new SparePartDAO();
        supplierDAO = new SupplierDAO();

        serviceController = new ServiceController(serviceManager, serviceView, serviceView.getTableModel());
        customerController = new CustomerController(customerManager, customerView, customerView.getTableModel());
        invoiceController = new InvoiceController(invoiceManager, invoiceView, invoiceView.getTableModel());
        sparePartController = new SparePartController(null, sparePartDAO);

        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "welcome");

        JPanel servicePanel = createServicePanel();
        cardPanel.add(servicePanel, "services");

        JPanel customerInvoicePanel = createCustomerInvoicePanel();
        cardPanel.add(customerInvoicePanel, "customers");

        JPanel invoicePanel = createInvoicePanel();
        cardPanel.add(invoicePanel, "invoices");

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void showMainWindow() {
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(227, 242, 253), 0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("CarMotors", CENTER);
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel("<html><center>Sistema de Gestión Automotriz<br>Selecciona una opción para comenzar</center></html>", CENTER);
        descriptionLabel.setForeground(new Color(66, 66, 66));
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(descriptionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        JButton inventoryButton = createStyledButton("Gestión de Inventarios", "📦");
        JButton servicesButton = createStyledButton("Mantenimiento y Reparaciones", "🔧");
        JButton customersButton = createStyledButton("Clientes y Facturación", "👥");
        JButton suppliersButton = createStyledButton("Proveedores y Compras", "🏬");
        JButton reportsButton = createStyledButton("Reportes y Estadísticas", "📊");

        inventoryButton.addActionListener(e -> {
            inventoryView = new SparePartView(frame);
            sparePartController.setView(inventoryView);
            inventoryView.setVisible(true);
            frame.setVisible(false);
        });

        servicesButton.addActionListener(e -> cardLayout.show(cardPanel, "services"));

        customersButton.addActionListener(e -> cardLayout.show(cardPanel, "customers"));

        suppliersButton.addActionListener(e -> {
            supplierController = new SupplierController(null, supplierDAO);
            supplierView = new SupplierView(frame, supplierController);
            supplierController.setView(supplierView);
            supplierView.setVisible(true);
            frame.setVisible(false);
        });

        reportsButton.addActionListener(e -> {
            new ReportView(frame, customerView, invoiceView, serviceView).setVisible(true);
        });

        buttonPanel.add(inventoryButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(servicesButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(customersButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(suppliersButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(reportsButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createServicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 240, 240));
        JLabel title = new JLabel("Gestión de Mantenimiento y Reparación", CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JTable table = new JTable(serviceView.getTableModel());
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(120);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(850, 400));
        scrollPane.setMinimumSize(new Dimension(750, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Regresar", new Color(25, 118, 210), 150, 30);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (serviceView.getRefreshButton() != null) {
            buttonPanel.add(serviceView.getRefreshButton());
        }
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCustomerInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("Gestión de Clientes y Facturación", CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Pestaña de Clientes
        JPanel customerPanel = new JPanel(new BorderLayout());
        JTable customerTable = new JTable(customerView.getTableModel());
        customerTable.setFillsViewportHeight(true);
        customerTable.setFont(new Font("Arial", Font.PLAIN, 12));
        customerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < customerTable.getColumnCount(); i++) {
            customerTable.getColumnModel().getColumn(i).setPreferredWidth(140);
        }
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        customerScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        customerScrollPane.setPreferredSize(new Dimension(850, 400));
        customerScrollPane.setMinimumSize(new Dimension(750, 300));
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Clientes", customerPanel);

        // Pestaña de Facturación
        JPanel invoicePanel = new JPanel(new BorderLayout());
        JTable invoiceTable = new JTable(invoiceView.getTableModel());
        invoiceTable.setFillsViewportHeight(true);
        invoiceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        invoiceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        invoiceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < invoiceTable.getColumnCount(); i++) {
            invoiceTable.getColumnModel().getColumn(i).setPreferredWidth(90);
        }
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);
        invoiceScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        invoiceScrollPane.setPreferredSize(new Dimension(850, 400));
        invoiceScrollPane.setMinimumSize(new Dimension(750, 300));
        invoicePanel.add(invoiceScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Facturación", invoicePanel);

        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Regresar", new Color(25, 118, 210), 150, 30);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JButton updateButton = createStyledButton("Actualizar Clientes", new Color(25, 118, 210), 150, 30);
        updateButton.addActionListener(e -> customerView.getRefreshButton().doClick());

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    updateButton.setText("Actualizar Clientes");
                    updateButton.removeActionListener(updateButton.getActionListeners()[0]);
                    updateButton.addActionListener(ev -> customerView.getRefreshButton().doClick());
                } else if (selectedIndex == 1) {
                    updateButton.setText("Actualizar Facturación");
                    updateButton.removeActionListener(updateButton.getActionListeners()[0]);
                    updateButton.addActionListener(ev -> invoiceView.getRefreshButton().doClick());
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 255));

        JLabel title = new JLabel("Gestión de Facturas", CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JTable table = new JTable(invoiceView.getTableModel());
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Regresar", new Color(25, 118, 210), 150, 30);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (invoiceView.getRefreshButton() != null) {
            buttonPanel.add(invoiceView.getRefreshButton());
        }
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton("<html><center>" + icon + "<br>" + text + "</center></html>");
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(new RoundedBorder(15));
        button.setPreferredSize(new Dimension(300, 80));
        button.setVerticalTextPosition(CENTER);
        button.setHorizontalTextPosition(CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(66, 165, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(25, 118, 210));
            }
        });

        return button;
    }

    private JButton createStyledButton(String text, Color backgroundColor, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new RoundedBorder(10));
        button.setPreferredSize(new Dimension(width, height));
        button.setVerticalTextPosition(CENTER);
        button.setHorizontalTextPosition(CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(66, 165, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(100, 100, 100, 50));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius, radius / 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius;
            insets.top = insets.bottom = radius / 2;
            return insets;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeScreen::new);
    }
}