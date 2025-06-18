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
import java.util.Calendar;
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
                    if (linea.trim().toLowerCase().startsWith("codigo")) {
                        continue;
                    }
                    String[] datos = linea.split("\\|");
                    if (datos.length >= 5) {
                        Cupon c = new Cupon();
                        c.codigo = datos[0].trim();
                        c.valor = datos[1].trim();
                        c.tipo = datos[2].trim();
                        c.fechavencimiento = datos[3].trim();

                        String fechaTexto = datos[4].trim();
                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.ENGLISH).parse(fechaTexto));
                            c.fecha = cal;
                        } catch (Exception ex) {
                            c.fecha = Calendar.getInstance(); // si no puede parsear, usa fecha actual
                        }

                        ProyectoPichardo.getCupones().add(c);
                        contador++;

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
