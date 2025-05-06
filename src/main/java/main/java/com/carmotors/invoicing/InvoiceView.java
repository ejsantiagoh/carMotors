/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
package main.java.com.carmotors.invoicing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.image.ImageDataFactory;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InvoiceView extends JPanel {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton viewQrButton;
    private JButton downloadOrEmailPdfButton;
    private InvoiceManager invoiceManager;

    // Datos del taller
    private static final String WORKSHOP_NAME = "Taller Automotriz CarMotors";
    private static final String WORKSHOP_NIT = "987654321-0";
    private static final String WORKSHOP_ADDRESS = "Calle Principal #12-36, Bucaramanga, Colombia";
    private static final String WORKSHOP_CONTACT = "Tel: (604) 555-5678 | Email: info@carmotors.com";
    private static final String DIGITAL_SIGNATURE = "FirmaDigitalCarMotors2025";

    public InvoiceView() {
        // Asegurar que la construcción se realice en el EDT
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::initializeUI);
        } else {
            initializeUI();
        }
    }

    private void initializeUI() {
        this.invoiceManager = new InvoiceManager();
        setLayout(new BorderLayout());

        // Configurar tabla
        String[] columnNames = {"ID", "Cliente ID", "Servicio ID", "Fecha", "Subtotal ($)", "Impuesto ($)", "Total ($)", "CUFE"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        invoiceTable = new JTable(tableModel);
        invoiceTable.setFillsViewportHeight(true);
        invoiceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        invoiceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        invoiceTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        refreshButton = new JButton("Actualizar Facturas");
        viewQrButton = new JButton("Ver QR");
        downloadOrEmailPdfButton = new JButton("Descargar o Enviar PDF");

        buttonPanel.add(refreshButton);
        buttonPanel.add(viewQrButton);
        buttonPanel.add(downloadOrEmailPdfButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Crear directorios para QRs y PDFs con verificación
        initializeDirectories();

        // Configurar listeners para los botones
        setupListeners();
    }

    private void setupListeners() {
        viewQrButton.addActionListener(e -> {
            int selectedRow = invoiceTable.getSelectedRow();
            if (selectedRow >= 0) {
                String cufe = tableModel.getValueAt(selectedRow, 7).toString();
                System.out.println("Botón Ver QR presionado. CUFE: " + cufe);
                showQrCode(cufe);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una factura para ver el QR.");
            }
        });

        downloadOrEmailPdfButton.addActionListener(e -> {
            int selectedRow = invoiceTable.getSelectedRow();
            if (selectedRow >= 0) {
                String cufe = tableModel.getValueAt(selectedRow, 7).toString();
                System.out.println("Botón Descargar o Enviar PDF presionado. CUFE: " + cufe);
                showDownloadOrEmailDialog(cufe, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una factura para descargar o enviar el PDF.");
            }
        });
    }

    private void initializeDirectories() {
        try {
            File qrDir = new File("qrcodes");
            if (!qrDir.exists()) {
                if (qrDir.mkdirs()) {
                    System.out.println("Directorio qrcodes creado: " + qrDir.getAbsolutePath());
                } else {
                    System.err.println("No se pudo crear el directorio qrcodes: " + qrDir.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "No se pudo crear el directorio qrcodes. Verifica los permisos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Directorio qrcodes ya existe: " + qrDir.getAbsolutePath());
            }

            File pdfDir = new File("pdfs");
            if (!pdfDir.exists()) {
                if (pdfDir.mkdirs()) {
                    System.out.println("Directorio pdfs creado: " + pdfDir.getAbsolutePath());
                } else {
                    System.err.println("No se pudo crear el directorio pdfs: " + pdfDir.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "No se pudo crear el directorio pdfs. Verifica los permisos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Directorio pdfs ya existe: " + pdfDir.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error al crear directorios: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear directorios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getViewQrButton() {
        return viewQrButton;
    }

    public JButton getDownloadOrEmailPdfButton() {
        return downloadOrEmailPdfButton;
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    }

    private void showQrCode(String cufe) {
        if (cufe == null || cufe.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El CUFE está vacío. No se puede generar el QR.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String qrContent = "https://comprobantefiscal.dian.gov.co/" + cufe + "?signature=" + DIGITAL_SIGNATURE;
        String filePath = "qrcodes/qrcode_" + cufe + ".png";
        File qrFile = new File(filePath);

        try {
            System.out.println("Generando QR para CUFE: " + cufe);
            System.out.println("Ruta del archivo QR: " + qrFile.getAbsolutePath());

            // Verificar permisos de escritura
            File qrDir = new File("qrcodes");
            if (!qrDir.canWrite()) {
                throw new IOException("No se tienen permisos de escritura en el directorio qrcodes: " + qrDir.getAbsolutePath());
            }

            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
            System.out.println("QR generado exitosamente en: " + qrFile.getAbsolutePath());

            if (!qrFile.exists()) {
                throw new IOException("El archivo QR no se creó en: " + filePath);
            }

            ImageIcon icon = new ImageIcon(filePath);
            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(this, new JLabel(scaledIcon), "Código QR", JOptionPane.PLAIN_MESSAGE);
        } catch (WriterException | IOException e) {
            System.err.println("Error al generar QR: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar QR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDownloadOrEmailDialog(String cufe, int row) {
        String[] options = {"Descargar PDF", "Enviar por Correo"};
        int choice = JOptionPane.showOptionDialog(this,
                "Selecciona una opción para la factura:",
                "Factura Electrónica",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            downloadPdf(cufe, row);
        } else if (choice == 1) {
            sendEmail(cufe, row);
        }
    }

    private void generatePdf(String cufe, int row, String destinationPath) {
        System.out.println("Intentando generar PDF en: " + destinationPath);
        File pdfFile = new File(destinationPath);

        try {
            // Verificar permisos de escritura
            File pdfDir = new File(pdfFile.getParent());
            if (!pdfDir.exists()) {
                if (!pdfDir.mkdirs()) {
                    throw new IOException("No se pudo crear el directorio pdfs: " + pdfDir.getAbsolutePath());
                }
                System.out.println("Directorio pdfs creado: " + pdfDir.getAbsolutePath());
            }

            if (!pdfDir.canWrite()) {
                throw new IOException("No se tienen permisos de escritura en el directorio pdfs: " + pdfDir.getAbsolutePath());
            }

            PdfWriter writer = new PdfWriter(destinationPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Información del taller
            document.add(new Paragraph(WORKSHOP_NAME)
                    .setFontSize(14)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("NIT: " + WORKSHOP_NIT)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(WORKSHOP_ADDRESS)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(WORKSHOP_CONTACT)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            // Título de la factura
            document.add(new Paragraph("\nFactura Electrónica")
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // Información de la factura
            document.add(new Paragraph("Factura No: " + tableModel.getValueAt(row, 0))
                    .setFontSize(10));
            document.add(new Paragraph("Fecha: " + tableModel.getValueAt(row, 3).toString())
                    .setFontSize(10));

            // Información del cliente
            Invoice invoice = invoiceManager.getInvoiceByRow(row, tableModel);
            if (invoice != null) {
                document.add(new Paragraph("Cliente: " + invoice.getCustomerName())
                        .setFontSize(10));
                document.add(new Paragraph("Documento: " + invoice.getCustomerDocument())
                        .setFontSize(10));
                document.add(new Paragraph("ID Dirección: " + invoice.getCustomerAddressId())
                        .setFontSize(10));
            } else {
                document.add(new Paragraph("Cliente ID: " + tableModel.getValueAt(row, 1))
                        .setFontSize(10));
            }

            document.add(new Paragraph("Servicio ID: " + tableModel.getValueAt(row, 2))
                    .setFontSize(10));

            // Detalle del servicio
            document.add(new Paragraph("\nDetalle del Servicio")
                    .setFontSize(12)
                    .setBold());
            Table table = new Table(4);
            table.addHeaderCell(new Cell().add(new Paragraph("Descripción")));
            table.addHeaderCell(new Cell().add(new Paragraph("Subtotal ($)")));
            table.addHeaderCell(new Cell().add(new Paragraph("Impuesto ($)")));
            table.addHeaderCell(new Cell().add(new Paragraph("Total ($)")));
            table.addCell(new Cell().add(new Paragraph("Servicio " + tableModel.getValueAt(row, 2))));
            table.addCell(new Cell().add(new Paragraph(tableModel.getValueAt(row, 4).toString())));
            table.addCell(new Cell().add(new Paragraph(tableModel.getValueAt(row, 5).toString())));
            table.addCell(new Cell().add(new Paragraph(tableModel.getValueAt(row, 6).toString())));
            document.add(table);

            // CUFE y QR
            String qrFilePath = "qrcodes/qrcode_" + cufe + ".png";
            document.add(new Paragraph("\nCUFE: " + tableModel.getValueAt(row, 7))
                    .setFontSize(10));

            File qrFile = new File(qrFilePath);
            if (qrFile.exists() && qrFile.canRead()) {
                System.out.println("Incluyendo QR en el PDF: " + qrFilePath);
                ImageData imageData = ImageDataFactory.create(qrFilePath);
                Image qrImage = new Image(imageData);
                qrImage.scaleToFit(100, 100);
                document.add(new Paragraph("\nCódigo QR:")
                        .setFontSize(10));
                document.add(qrImage);
            } else {
                System.out.println("QR no disponible para incluir en el PDF: " + qrFilePath);
                document.add(new Paragraph("\nCódigo QR no disponible")
                        .setFontSize(10));
            }

            // Firma digital
            document.add(new Paragraph("\nFirma Digital: " + DIGITAL_SIGNATURE)
                    .setFontSize(10));

            document.close();
            pdfDoc.close();
            writer.close();

            if (!pdfFile.exists()) {
                System.err.println("El archivo PDF no se creó después de cerrar los recursos.");
                throw new IOException("El archivo PDF no se creó en: " + destinationPath);
            } else {
                System.out.println("PDF generado exitosamente en: " + destinationPath);
            }
        } catch (Exception e) {
            System.err.println("Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }

    private void downloadPdf(String cufe, int row) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona dónde guardar el PDF");
        String fileName = "invoice_" + tableModel.getValueAt(row, 0) + ".pdf";
        fileChooser.setSelectedFile(new File(fileName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String destinationPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!destinationPath.toLowerCase().endsWith(".pdf")) {
                destinationPath += ".pdf";
            }

            try {
                generatePdf(cufe, row, destinationPath);
                File pdfFile = new File(destinationPath);
                if (pdfFile.exists()) {
                    JOptionPane.showMessageDialog(this, "PDF generado en: " + destinationPath);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El PDF no se generó en: " + destinationPath);
                }
            } catch (Exception e) {
                System.err.println("Error en downloadPdf: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage());
            }
        }
    }

    private void sendEmail(String cufe, int row) {
        String fileName = "invoice_" + tableModel.getValueAt(row, 0) + ".pdf";
        String pdfsDir = "pdfs";
        String destinationPath = pdfsDir + File.separator + fileName;

        try {
            File pdfsDirFile = new File(pdfsDir);
            if (!pdfsDirFile.exists()) {
                if (!pdfsDirFile.mkdirs()) {
                    throw new IOException("No se pudo crear la carpeta pdfs: " + pdfsDirFile.getAbsolutePath());
                }
            }

            generatePdf(cufe, row, destinationPath);

            File pdfFile = new File(destinationPath);
            if (pdfFile.exists()) {
                JOptionPane.showMessageDialog(this, "PDF generado y enviado al correo del cliente (simulado).\nArchivo guardado en: " + destinationPath);
            } else {
                JOptionPane.showMessageDialog(this, "Error: El PDF no se generó, por lo que no se pudo enviar.");
            }
        } catch (Exception e) {
            System.err.println("Error en sendEmail: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar o enviar el PDF: " + e.getMessage());
        }
    }
}