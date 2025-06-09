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
/**
 *
 * @author oem
 */
public class LectorDirecciones {
    public static void cargarDireccionesDesdeArchivo() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona el archivo de direcciones");

    int resultado = fileChooser.showOpenDialog(null);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 4) {
                    DireccionV direccion = new DireccionV();
                    direccion.calle = datos[0].trim();
                    direccion.avenida = datos[1].trim();
                    direccion.direccion = datos[2].trim();
                    direccion.zona = Integer.parseInt(datos[3].trim());

                    ProyectoPichardo.getDireccionesv().add(direccion);
                    contador++;

                    // Mostrar en consola
                    System.out.println("Dirección cargada:");
                    System.out.println("Calle: " + direccion.calle);
                    System.out.println("Avenida: " + direccion.avenida);
                    System.out.println("Dirección: " + direccion.direccion);
                    System.out.println("Zona: " + direccion.zona);
                    System.out.println("--------------------");
                }
            }

            // Mensaje final
            StringBuilder mensaje = new StringBuilder("Direcciones cargadas:\n");
            for (DireccionV d : ProyectoPichardo.getDireccionesv()) {
                mensaje.append("Calle: ").append(d.calle)
                       .append(", Avenida: ").append(d.avenida)
                       .append(", Dirección: ").append(d.direccion)
                       .append(", Zona: ").append(d.zona)
                       .append("\n");
            }

            JOptionPane.showMessageDialog(null, contador + " dirección(es) cargada(s) exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Operación cancelada.");
    }
}

}
