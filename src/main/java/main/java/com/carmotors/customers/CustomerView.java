/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.customers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author fashe
 */


public class CustomerView extends JPanel {
    private JTextArea customerList;
    private JButton refreshButton;

    public CustomerView() {
        setLayout(new BorderLayout());

        customerList = new JTextArea(10, 50);
        customerList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(customerList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        refreshButton = new JButton("Actualizar Clientes");
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setCustomerList(String customers) {
        customerList.setText(customers);
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}