/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LecturaUsuariosJSON {
    public static void cargarUsuariosDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo JSON de usuarios");

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

                    Usuario u = new Usuario();
                    u.setNombre(obj.getString("nombre"));
                    u.setUsuario(obj.getString("usuario"));
                    u.setPassword(obj.getString("password"));
                    u.setRol(obj.getInt("rol")); // 1 = Admin, 2 = Vendedor
                    if (obj.has("fecha")) {
    try {
        String fechaTexto = obj.getString("fecha");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(fechaTexto));
        u.setFecha(cal);
    } catch (Exception e) {
        u.setFecha(Calendar.getInstance()); // si hay error, usa fecha actual
    }
} else {
    u.setFecha(Calendar.getInstance()); // si no viene la fecha
}

                    ProyectoPichardo.getUsuarios().add(u);
                    contador++;
                }

                JOptionPane.showMessageDialog(null, contador + " usuario(s) cargado(s) exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo JSON:\n" + e.getMessage());
            }
        }
    }

    public static void guardarUsuariosComoJSON() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar usuarios como archivo JSON");

        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            JSONArray array = new JSONArray();
            for (Usuario u : ProyectoPichardo.getUsuarios()) {
                JSONObject obj = new JSONObject();
                obj.put("nombre", u.getNombre());
                obj.put("usuario", u.getUsuario());
                obj.put("password", u.getPassword());
                obj.put("rol", u.getRol());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String fechaTexto = (u.getFecha() != null) ? sdf.format(u.getFecha().getTime()) : "";
obj.put("fecha", fechaTexto);
                array.put(obj);
            }

            try (PrintWriter writer = new PrintWriter(archivo)) {
                writer.write(array.toString(4)); // indentado
                JOptionPane.showMessageDialog(null, "Usuarios exportados exitosamente a JSON.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar archivo JSON: " + e.getMessage());
            }
        }
    }
}
