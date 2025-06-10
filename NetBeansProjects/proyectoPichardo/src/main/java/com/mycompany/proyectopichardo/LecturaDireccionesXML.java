/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
/**
 *
 * @author oem
 */
public class LecturaDireccionesXML {
     // Cargar direcciones desde archivo XML
    public static void cargarDireccionesDesdeXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo XML de direcciones");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("direccion");
                int contador = 0;

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;

                        DireccionV d = new DireccionV();
                        d.calle = getTagValue(elemento, "calle");
                        d.avenida = getTagValue(elemento, "avenida");
                        d.direccion = getTagValue(elemento, "direccion");
                        String zonaTexto = getTagValue(elemento, "zona");
if (!zonaTexto.isEmpty()) {
    d.zona = Integer.parseInt(zonaTexto);
} else {
    d.zona = 0; // o muestra un mensaje si prefieres
}

                        ProyectoPichardo.getDireccionesv().add(d);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " dirección(es) cargada(s) correctamente.");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el XML:\n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Guardar direcciones en archivo XML
    public static void guardarDireccionesComoXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar direcciones como archivo XML");

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
                writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                writer.println("<direcciones>");

                for (DireccionV d : ProyectoPichardo.getDireccionesv()) {
                    writer.println("  <direccion>");
                    writer.println("    <calle>" + d.calle + "</calle>");
                    writer.println("    <avenida>" + d.avenida + "</avenida>");
                    writer.println("    <direccion>" + d.direccion + "</direccion>");
                    writer.println("    <zona>" + d.zona + "</zona>");
                    writer.println("  </direccion>");
                }

                writer.println("</direcciones>");
                JOptionPane.showMessageDialog(null, "Direcciones exportadas correctamente a:\n" + archivo.getAbsolutePath());
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
