/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.inventory;

/**
 *
 * @author yorle
 */

// Spare part controller
public class SparePartController {
    private SparePartView view;
    private SparePartDAO dao;

    public SparePartController(SparePartView view, SparePartDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void addSparePart(String name, String code) {
        // SparePart part = new SparePart();
        // lógica de control
    }
}

