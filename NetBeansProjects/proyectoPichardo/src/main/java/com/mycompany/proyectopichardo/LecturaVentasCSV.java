/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author oem
 */
public class LecturaVentasCSV {
    public static void cargarVentasDesdeCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo CSV de ventas");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                boolean esPrimeraLinea = true;

                while ((linea = br.readLine()) != null) {
                    // Saltar encabezado
                    if (esPrimeraLinea && linea.toLowerCase().contains("nombre,nit,direccion")) {
                        esPrimeraLinea = false;
                        continue;
                    }
                    esPrimeraLinea = false;

                    String[] datos = linea.split(",");

                    if (datos.length != 7) {
                        System.out.println("Línea inválida: " + linea);
                        continue;
                    }

                    Venta v = new Venta();
                    v.nombre = datos[0].trim();
                    v.nit = datos[1].trim();
                    v.direccion = datos[2].trim();
                    v.total = Double.parseDouble(datos[3].trim());
                    v.totalSinIVA = Double.parseDouble(datos[4].trim());
                    v.vendedor = datos[5].trim();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar fecha = Calendar.getInstance();
                    fecha.setTime(sdf.parse(datos[6].trim()));
                    v.fecha = fecha;

                    ProyectoPichardo.getVentas().add(v);
                    contador++;
                }

                JOptionPane.showMessageDialog(null, contador + " venta(s) agregada(s) correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo:\n" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
        }
    }
}
