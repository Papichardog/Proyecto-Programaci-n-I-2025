/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.*;
/**
 *
 * @author oem
 */
public class PersistenciaBinaria {
    private static final String RUTA = "datosGuardados.dat";

    public static void guardarTodo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            DatosGuardados datos = new DatosGuardados();
            datos.usuarios = ProyectoPichardo.getUsuarios();
            datos.libros = ProyectoPichardo.getLibros();
            datos.cupones = ProyectoPichardo.getCupones();
            datos.ventas = ProyectoPichardo.getVentas();
            datos.librosv = ProyectoPichardo.getLibrosv();
            datos.direccionesv = ProyectoPichardo.getDireccionesv();
            out.writeObject(datos);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
}
public static void cargarTodo() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return; // Si no existe, no hacer nada

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            DatosGuardados datos = (DatosGuardados) in.readObject();
            ProyectoPichardo.setUsuarios(datos.usuarios);
            ProyectoPichardo.setLibros(datos.libros);
            ProyectoPichardo.setCupones(datos.cupones);
            ProyectoPichardo.setVentas(datos.ventas);
            ProyectoPichardo.setLibrosv(datos.librosv);
            ProyectoPichardo.setDireccionesv(datos.direccionesv);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


