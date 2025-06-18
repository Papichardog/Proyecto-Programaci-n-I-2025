/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author oem
 */
public class LecturaLibrosJSON {

    public static void cargarLibrosDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo JSON de libros");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(new FileReader(archivo))) {
                StringBuilder json = new StringBuilder();
                while (scanner.hasNextLine()) {
                    json.append(scanner.nextLine());
                }

                JSONArray arreglo = new JSONArray(json.toString());

                int contador = 0;
                for (int i = 0; i < arreglo.length(); i++) {
                    JSONObject objeto = arreglo.getJSONObject(i);
                    Libros libro = new Libros();
                    libro.titulo = objeto.getString("titulo");
                    libro.autor = objeto.getString("autor");
                    libro.genero = objeto.getString("genero");
                    libro.precio = objeto.getDouble("precio");
                    libro.stock = objeto.getInt("stock");

                    // ✅ Leer la fecha (si existe)
                    if (objeto.has("fecha")) {
                        Calendar fecha = Calendar.getInstance();
                        fecha.setTimeInMillis(objeto.getLong("fecha"));
                        libro.fecha = fecha;
                    } else {
                        libro.fecha = Calendar.getInstance(); // evitar null
                    }

                    ProyectoPichardo.getLibros().add(libro);
                    contador++;
                }

                JOptionPane.showMessageDialog(null, contador + " libro(s) cargado(s) correctamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el JSON: " + e.getMessage());
            }
        }
    }
}
