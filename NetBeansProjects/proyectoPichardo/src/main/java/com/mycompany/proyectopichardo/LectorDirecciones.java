/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.Calendar;
/**
 *
 * @author oem
 */
public class LectorDirecciones {
    public static void cargarDireccionesDesdeArchivo() {
    JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV de direcciones");

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                boolean esPrimera = true;

                while ((linea = br.readLine()) != null) {
                    if (esPrimera) {
                        esPrimera = false; // Saltar encabezado
                        continue;
                    }

                    String[] datos = linea.split("\\|");
                    if (datos.length >= 5) {
                        DireccionV d = new DireccionV();
                        d.calle = datos[0].trim();
                        d.avenida = datos[1].trim();
                        d.direccion = datos[2].trim();
                        d.zona = Integer.parseInt(datos[3].trim());

                        try {
                            long millis = Long.parseLong(datos[4].trim());
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(millis);
                            d.fecha = cal;
                        } catch (Exception e) {
                            d.fecha = Calendar.getInstance();
                        }

                        ProyectoPichardo.getDireccionesv().add(d);
                        contador++;
                    }
                }

                JOptionPane.showMessageDialog(null, contador + " dirección(es) cargada(s) exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            }
        }
}
}