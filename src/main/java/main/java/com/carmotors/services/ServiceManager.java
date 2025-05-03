/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.carmotors.services;


import java.util.List;

/**
 *
 * @author fashe
 */


public class ServiceManager {
    private final ServiceDAO serviceDAO;

    public ServiceManager() {
        this.serviceDAO = new ServiceDAO();
    }

    public List<Service> listServices() {
        return serviceDAO.listServices();
    }
}