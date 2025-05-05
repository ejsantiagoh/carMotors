/*
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to change this license
 * Click https://netbeans.apache.org/project_downloads/platform/javadoc/ to edit this template
 */
package main.java.com.carmotors.app;

/**
 *
 * @author fashe
 */

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos para evitar problemas con Swing
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomeScreen(); // Crear la instancia y dejar que maneje su visibilidad
            }
        });
    }
}
