/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author oem
 */
public class LecturaCuponesXML {

    // Leer cupones desde XML
    public static void cargarCuponesDesdeXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo XML de cupones");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("cupon");
                int contador = 0;

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;

                        Cupon cupon = new Cupon(
                                getTagValue(elemento, "codigo"),
                                getTagValue(elemento, "valor"),
                                getTagValue(elemento, "tipo"),
                                getTagValue(elemento, "fechavencimiento")
                        );
                        String fechaTexto = getTagValue(elemento, "fechacreacion");

                        if (!fechaTexto.isEmpty()) {
                            try {
                                long millis = Long.parseLong(fechaTexto);
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(millis);
                                cupon.fecha = cal;
                            } catch (NumberFormatException e) {
                                cupon.fecha = Calendar.getInstance(); // Usa actual si falla el parseo
                            }
                        } else {
                            cupon.fecha = Calendar.getInstance(); // Si no hay campo, usa actual
                        }

                        ProyectoPichardo.getCupones().add(cupon);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " cupón(es) cargado(s) correctamente.");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el XML:\n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Guardar cupones como XML
    public static void guardarCuponesComoXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar cupones como archivo XML");

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
                writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                writer.println("<cupones>");

                for (Cupon c : ProyectoPichardo.getCupones()) {
                    writer.println("  <cupon>");
                    writer.println("    <codigo>" + c.codigo + "</codigo>");
                    writer.println("    <valor>" + c.valor + "</valor>");
                    writer.println("    <tipo>" + c.tipo + "</tipo>");
                    writer.println("    <fechavencimiento>" + c.fechavencimiento + "</fechavencimiento>");
                    writer.println("    <fechacreacion>"
                            + (c.fecha != null ? c.fecha.getTimeInMillis() : "")
                            + "</fechacreacion>");
                    writer.println("  </cupon>");
                }

                writer.println("</cupones>");
                JOptionPane.showMessageDialog(null, "Cupones exportados exitosamente a:\n" + archivo.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage());
            }
        }
    }

    private static String getTagValue(Element element, String tag) {
        NodeList nodes = element.getElementsByTagName(tag);
        if (nodes.getLength() > 0 && nodes.item(0) != null) {
            return nodes.item(0).getTextContent().trim();
        } else {
            return "";
        }
    }
}
