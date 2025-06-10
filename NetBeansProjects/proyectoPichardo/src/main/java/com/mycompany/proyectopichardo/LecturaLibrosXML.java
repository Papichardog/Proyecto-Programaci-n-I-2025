/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/**
 *
 * @author oem
 */
public class LecturaLibrosXML {
     // Cargar desde archivo XML a lista de libros
    public static void cargarLibrosDesdeXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo XML de libros");

        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("libro");
                int contador = 0;

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;

                        Libros libro = new Libros();
                        libro.titulo = getTagValue(elemento, "titulo");
                        libro.autor = getTagValue(elemento, "autor");
                        libro.genero = getTagValue(elemento, "genero");
                        libro.precio = Double.parseDouble(getTagValue(elemento, "precio"));
                        libro.stock = Integer.parseInt(getTagValue(elemento, "stock"));

                        ProyectoPichardo.getLibros().add(libro);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " libro(s) cargado(s) exitosamente.");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el XML: " + e.getMessage());
            }
        }
    }

    // Guardar la lista de libros a archivo XML
    public static void guardarLibrosComoXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar libros en archivo XML");

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
                writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                writer.println("<libros>");

                for (Libros libro : ProyectoPichardo.getLibros()) {
                    writer.println("  <libro>");
                    writer.println("    <titulo>" + libro.titulo + "</titulo>");
                    writer.println("    <autor>" + libro.autor + "</autor>");
                    writer.println("    <genero>" + libro.genero + "</genero>");
                    writer.println("    <precio>" + libro.precio + "</precio>");
                    writer.println("    <stock>" + libro.stock + "</stock>");
                    writer.println("  </libro>");
                }

                writer.println("</libros>");
                JOptionPane.showMessageDialog(null, "Libros guardados exitosamente en:\n" + archivo.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar archivo XML: " + e.getMessage());
            }
        }
    }

    // Obtener valor de una etiqueta
    private static String getTagValue(Element element, String tag) {
        NodeList nodes = element.getElementsByTagName(tag);
        if (nodes.getLength() > 0 && nodes.item(0) != null) {
            return nodes.item(0).getTextContent().trim();
        } else {
            return "";
        }
    }
}
