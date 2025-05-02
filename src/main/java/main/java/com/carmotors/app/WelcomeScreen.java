/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.app;

import main.java.com.carmotors.customers.CustomerManager;
import main.java.com.carmotors.invoicing.InvoiceManager;
import main.java.com.carmotors.services.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fashe
 */

public class WelcomeScreen {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ServiceView serviceView;
    private CustomerManager customerManager;
    private InvoiceManager invoiceManager;

    public WelcomeScreen() {
        frame = new JFrame("CarMotors - Gestión de Servicios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Inicializar managers y view
        serviceView = new ServiceView();
        customerManager = new CustomerManager();
        invoiceManager = new InvoiceManager();
        new ServiceController(new ServiceManager(), customerManager, invoiceManager, serviceView);

        // Pantalla de bienvenida
        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "welcome");

        // Añadir panel de servicios
        cardPanel.add(serviceView, "services");

        // Panel de clientes
        JPanel customerPanel = createCustomerPanel();
        cardPanel.add(customerPanel, "customers");

        // Panel de facturas
        JPanel invoicePanel = createInvoicePanel();
        cardPanel.add(invoicePanel, "invoices");

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Bienvenido a CarMotors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel("<html><center>Gestión de Servicios Automotrices<br>Seleccione una sección para comenzar</center></html>", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(descriptionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton servicesButton = new JButton("Ir a Servicios");
        JButton customersButton = new JButton("Ir a Clientes");
        JButton invoicesButton = new JButton("Ir a Facturas");

        servicesButton.addActionListener(e -> cardLayout.show(cardPanel, "services"));
        customersButton.addActionListener(e -> cardLayout.show(cardPanel, "customers"));
        invoicesButton.addActionListener(e -> cardLayout.show(cardPanel, "invoices"));

        buttonPanel.add(servicesButton);
        buttonPanel.add(customersButton);
        buttonPanel.add(invoicesButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JTextArea info = new JTextArea("Aquí puedes listar y gestionar la información de los clientes.\n" +
                                      "Ejemplo: John Doe, Jane Smith.\n" +
                                      "Haz clic en 'Listar Clientes' para ver los datos.");
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(info), BorderLayout.CENTER);

        JButton listButton = new JButton("Listar Clientes");
        listButton.addActionListener(e -> {
            try {
                serviceView.displayCustomers(customerManager.listCustomers());
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(frame, "Error al listar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(listButton);
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

        JTextArea info = new JTextArea("Aquí puedes listar y gestionar las facturas generadas.\n" +
                                      "Ejemplo: Facturas con subtotal, impuestos y total.\n" +
                                      "Haz clic en 'Listar Facturas' para ver los datos.");
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(info), BorderLayout.CENTER);

        JButton listButton = new JButton("Listar Facturas");
        listButton.addActionListener(e -> {
            try {
                serviceView.displayInvoices(invoiceManager.listInvoices());
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(frame, "Error al listar facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(listButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeScreen::new);
    }
}