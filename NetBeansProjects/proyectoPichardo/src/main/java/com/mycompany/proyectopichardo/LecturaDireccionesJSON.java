/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class LecturaDireccionesJSON {
    public static void cargarDireccionesDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo JSON de direcciones");

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(archivo)) {
                StringBuilder json = new StringBuilder();
                while (scanner.hasNextLine()) {
                    json.append(scanner.nextLine());
                }

                JSONArray array = new JSONArray(json.toString());
                int contador = 0;

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    DireccionV d = new DireccionV();
                    d.calle = obj.getString("calle");
                    d.avenida = obj.getString("avenida");
                    d.direccion = obj.getString("direccion");
                    d.zona = obj.getInt("zona");

                    ProyectoPichardo.getDireccionesv().add(d);
                    contador++;
                }

                JOptionPane.showMessageDialog(null, contador + " dirección(es) cargada(s) exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer archivo JSON:\n" + e.getMessage());
            }
        }
    }

    public static void guardarDireccionesComoJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar direcciones como archivo JSON");

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            JSONArray array = new JSONArray();
            for (DireccionV d : ProyectoPichardo.getDireccionesv()) {
                JSONObject obj = new JSONObject();
                obj.put("calle", d.calle);
                obj.put("avenida", d.avenida);
                obj.put("direccion", d.direccion);
                obj.put("zona", d.zona);
                array.put(obj);
            }

            try (PrintWriter writer = new PrintWriter(archivo)) {
                writer.write(array.toString(4));
                JOptionPane.showMessageDialog(null, "Direcciones exportadas exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar archivo: " + e.getMessage());
            }
        }
    }
}
