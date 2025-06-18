/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

/**
 *
 * @author oem
 */
public class LecturaLibros {

    public static void cargarLibrosDesdeCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo CSV de libros");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                boolean esPrimera = true;

                // ✅ Formato para leer la fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                while ((linea = br.readLine()) != null) {
                    if (esPrimera) {
                        esPrimera = false; // Saltar encabezado
                        continue;
                    }

                    String[] datos = linea.split("\\|");

                    if (datos.length >= 6) {
                        Libros libro = new Libros();
                        libro.titulo = datos[0].trim();
                        libro.autor = datos[1].trim();
                        libro.genero = datos[2].trim();
                        libro.precio = Double.parseDouble(datos[3].trim());
                        libro.stock = Integer.parseInt(datos[4].trim());

                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(datos[5].trim()));
                            libro.fecha = cal;
                        } catch (Exception e) {
                            libro.fecha = Calendar.getInstance(); // usar actual si hay error
                        }

                        ProyectoPichardo.getLibros().add(libro);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " libro(s) cargado(s) exitosamente.");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
        }
    }
}
