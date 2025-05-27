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
public class LectorCupones {

    public static void cargarCuponesDesdeArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo de cupones");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 4) {
                        Cupon cupon = new Cupon(
                                datos[0].trim(),
                                datos[1].trim(),
                                datos[2].trim(),
                                datos[3].trim()
                        );
                        ProyectoPichardo.getCupones().add(cupon);
                        contador++;

                        // Mostrar en consola:
                        System.out.println("Cupon cargado:");
                        System.out.println("Código: " + cupon.codigo);
                        System.out.println("Valor: " + cupon.valor);
                        System.out.println("Tipo: " + cupon.tipo);
                        System.out.println("Fecha de vencimiento: " + cupon.fechavencimiento);
                        System.out.println("--------------------");
                    }
                }
// Construir un mensaje para mostrar todos los cupones
                StringBuilder mensaje = new StringBuilder("Cupones cargados:\n");
                for (Cupon c : ProyectoPichardo.getCupones()) {
                    mensaje.append("Código: ").append(c.codigo)
                            .append(", Valor: ").append(c.valor)
                            .append(", Tipo: ").append(c.tipo)
                            .append(", Vence: ").append(c.fechavencimiento)
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, contador + " cupon(es) cargado(s) exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
        }
    }
}
