/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
/**
 *
 * @author oem
 */
public class LecturaCuponesJSON {
    // Cargar desde JSON
    public static void cargarCuponesDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona el archivo JSON de cupones");

    int resultado = fileChooser.showOpenDialog(null);
    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        try (Scanner scanner = new Scanner(new FileReader(archivo))) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            JSONArray array = new JSONArray(json.toString());
            int contador = 0;

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Cupon c = new Cupon(
                        obj.getString("codigo"),
                        obj.getString("valor"),
                        obj.getString("tipo"),
                        obj.getString("fechavencimiento")
                );

                // Leer fecha de creación como milisegundos
                if (obj.has("fecha")) {
                    long millis = obj.getLong("fecha");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(millis);
                    c.fecha = cal;
                } else {
                    c.fecha = Calendar.getInstance();
                }

                ProyectoPichardo.getCupones().add(c);
                contador++;
            }

            JOptionPane.showMessageDialog(null, contador + " cupón(es) cargado(s) exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo JSON: " + e.getMessage());
        }
    }
        
    }

    // Exportar a JSON
    public static void guardarCuponesComoJSON() {
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar cupones como archivo JSON");

    int seleccion = fileChooser.showSaveDialog(null);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        JSONArray array = new JSONArray();

        for (Cupon c : ProyectoPichardo.getCupones()) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", c.codigo);
            obj.put("valor", c.valor);
            obj.put("tipo", c.tipo);
            obj.put("fechavencimiento", c.fechavencimiento);

            long millis = (c.fecha != null) ? c.fecha.getTimeInMillis() : Calendar.getInstance().getTimeInMillis();
            obj.put("fecha", millis);

            array.put(obj);
        }

        try (PrintWriter writer = new PrintWriter(archivo)) {
            writer.write(array.toString(4));
            JOptionPane.showMessageDialog(null, "Cupones guardados en JSON exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar archivo JSON: " + e.getMessage());
        }
    }
    }
}
