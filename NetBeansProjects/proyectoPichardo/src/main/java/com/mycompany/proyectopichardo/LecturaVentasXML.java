/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.awt.HeadlessException;

/**
 *
 * @author oem
 */
public class LecturaVentasXML {
    public static void exportarVentasAXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar ventas como XML");

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element root = doc.createElement("ventas");
                doc.appendChild(root);

                for (Venta v : ProyectoPichardo.getVentas()) {
                    Element venta = doc.createElement("venta");

                    venta.appendChild(crearElemento(doc, "nombre", v.nombre));
                    venta.appendChild(crearElemento(doc, "nit", v.nit));
                    venta.appendChild(crearElemento(doc, "direccion", v.direccion));
                    venta.appendChild(crearElemento(doc, "total", String.valueOf(v.total)));
                    venta.appendChild(crearElemento(doc, "totalSinIVA", String.valueOf(v.totalSinIVA)));
                    venta.appendChild(crearElemento(doc, "vendedor", v.vendedor));
                    venta.appendChild(crearElemento(doc, "fecha", String.valueOf(v.fecha.getTimeInMillis())));

                    root.appendChild(venta);
                }

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                transformer.transform(new DOMSource(doc), new StreamResult(archivo));

                JOptionPane.showMessageDialog(null, "Ventas exportadas a XML correctamente.");

            } catch (ParserConfigurationException | TransformerException | HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Error al exportar ventas: " + e.getMessage());
            }
        }
    }

    public static void cargarVentasDesdeXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo XML de ventas");

        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("venta");
                int contador = 0;

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;

                        Venta v = new Venta();
                        v.nombre = getTagValue(elemento, "nombre");
                        v.nit = getTagValue(elemento, "nit");
                        v.direccion = getTagValue(elemento, "direccion");
                        v.total = Double.parseDouble(getTagValue(elemento, "total"));
                        v.totalSinIVA = Double.parseDouble(getTagValue(elemento, "totalSinIVA"));
                        v.vendedor = getTagValue(elemento, "vendedor");
                        long fechaMillis = Long.parseLong(getTagValue(elemento, "fecha"));
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(fechaMillis);
                        v.fecha = cal;

                        ProyectoPichardo.getVentas().add(v);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " venta(s) cargada(s) desde XML.");

            } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar XML: " + e.getMessage());
            }
        }
    }

    private static Element crearElemento(Document doc, String etiqueta, String valor) {
        Element elemento = doc.createElement(etiqueta);
        elemento.appendChild(doc.createTextNode(valor));
        return elemento;
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
