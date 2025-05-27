/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 *
 * @author oem
 */
public class LectorXMLUsuarios {
    public static void cargarUsuariosDesdeXML() {
      
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo XML de usuarios");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("usuario");
                int contador = 0;

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;

                        String nombre = getTagValue(elemento, "nombre");
                        String usuario = getTagValue(elemento, "usuario");
                        String password = getTagValue(elemento, "password");
                        String rolTexto = getTagValue(elemento, "rol").toLowerCase();

                        // Verifica que los campos esenciales no estén vacíos
                        if (!usuario.isEmpty() && !password.isEmpty()) {
                            Usuario u = new Usuario();
                            u.setNombre(nombre);
                            u.setUsuario(usuario);
                            u.setPassword(password);
                            u.setRol(rolTexto.equals("administrador") ? 1 : 2);

                            ProyectoPichardo.getUsuarios().add(u);
                            contador++;
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " usuario(s) cargado(s) correctamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el XML:\n" + e.getMessage());
                e.printStackTrace();
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
