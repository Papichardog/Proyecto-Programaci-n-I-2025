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
public class LecturaVentasJSON {
    public static void cargarVentasDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo JSON de ventas");

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

                    Venta v = new Venta();
                    v.nombre = obj.getString("nombre");
                    v.nit = obj.getString("nit");
                    v.direccion = obj.getString("direccion");
                    v.vendedor = obj.getString("vendedor");
                    v.total = obj.getDouble("total");

                    // Parsear fecha
                    long tiempoMillis = obj.getLong("fecha");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(tiempoMillis);
                    v.fecha = cal;

                    // Libros vendidos
                    v.librosVendidos = new ArrayList<>();
                    JSONArray librosArray = obj.getJSONArray("librosVendidos");
                    for (int j = 0; j < librosArray.length(); j++) {
                        JSONObject l = librosArray.getJSONObject(j);
                        LibroV lib = new LibroV();
                        lib.titulo = l.getString("titulo");
                        lib.precio = l.getDouble("precio");
                        lib.stock = l.getInt("stock"); // cantidad vendida
                        v.librosVendidos.add(lib);
                    }

                    ProyectoPichardo.getVentas().add(v);
                    contador++;
                }

                JOptionPane.showMessageDialog(null, contador + " venta(s) cargada(s) exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer archivo JSON:\n" + e.getMessage());
            }
        }
    }

    public static void guardarVentasComoJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar ventas como archivo JSON");

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            JSONArray array = new JSONArray();
            for (Venta v : ProyectoPichardo.getVentas()) {
                JSONObject obj = new JSONObject();
                obj.put("nombre", v.nombre);
                obj.put("nit", v.nit);
                obj.put("direccion", v.direccion);
                obj.put("vendedor", v.vendedor);
                obj.put("total", v.total);
                obj.put("fecha", v.fecha.getTimeInMillis());

                JSONArray librosArray = new JSONArray();
                for (LibroV l : v.librosVendidos) {
                    JSONObject libroObj = new JSONObject();
                    libroObj.put("titulo", l.titulo);
                    libroObj.put("precio", l.precio);
                    libroObj.put("stock", l.stock); // cantidad vendida
                    librosArray.put(libroObj);
                }

                obj.put("librosVendidos", librosArray);
                array.put(obj);
            }

            try (PrintWriter writer = new PrintWriter(archivo)) {
                writer.write(array.toString(4)); // Formato indentado
                JOptionPane.showMessageDialog(null, "Ventas exportadas exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar archivo: " + e.getMessage());
            }
        }
    }
}
