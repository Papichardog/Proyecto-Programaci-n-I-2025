/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectopichardo;

import java.util.ArrayList;

/**
 *
 * @author oem
 */
public class ProyectoPichardo {

    

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Libros> libros = new ArrayList<>();
    private static ArrayList<Cupon> cupones = new ArrayList<>();
    private static ArrayList<Venta> ventas = new ArrayList<>();
    private static ArrayList<LibroV> librosv = new ArrayList<>();
    private static ArrayList<DireccionV> direccionesv = new ArrayList<>();

    public static void main(String[] args) {
        Usuario u = new Usuario();
        u.setNombre("admin");
        u.setUsuario("admin");
        u.setPassword("admin");
        u.setRol(1);
        
        getUsuarios().add(u);
        
        
        
        // Libros predeterminados
    Libros libro1 = new Libros();
    libro1.titulo = "El Principito";
    libro1.autor = "Don Voltaire";
    libro1.genero = "Aventura";
    libro1.precio = 20.99;
    libro1.stock = 10;

    Libros libro2 = new Libros();
    libro2.titulo = "Romeo y Julieta";
    libro2.autor = "William Shakespeare";
    libro2.genero = "Novelas";
    libro2.precio = 25.99;
    libro2.stock = 15;
    
    // Libros para venta (LibrosV)
    LibroV libroV1 = new LibroV();
    libroV1.titulo = "El Principito";
    libroV1.precio = 20.99;
    libroV1.stock = 10;

    LibroV libroV2 = new LibroV();
    libroV2.titulo = "Romeo y Julieta";
    libroV2.precio = 25.99;
    libroV2.stock = 15;

    getLibrosv().add(libroV1);
    getLibrosv().add(libroV2);

    getLibros().add(libro1);
    getLibros().add(libro2);
        
    // Cupones predeterminados
Cupon cupon1 = new Cupon("01", "4.99", "Monto fijo", "12/12/26");
Cupon cupon2 = new Cupon("02", "10", "Porcentual", "13/12/26");

getCupones().add(cupon1);
getCupones().add(cupon2);
    
        Login v = new Login();
        v.setVisible(true);
    }
private static Usuario usuarioActual;


    /**
     * @return the usuarios
     */
    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param aUsuarios the usuarios to set
     */
    public static void setUsuarios(ArrayList<Usuario> aUsuarios) {
        usuarios = aUsuarios;
    }

    /**
     * @return the libros
     */
    public static ArrayList<Libros> getLibros() {
        return libros;
    }

    /**
     * @param aLibros the libros to set
     */
    public static void setLibros(ArrayList<Libros> aLibros) {
        libros = aLibros;
    }

    /**
     * @return the cupones
     */
    public static ArrayList<Cupon> getCupones() {
        return cupones;
    }

    /**
     * @param aCupones the cupones to set
     */
    public static void setCupones(ArrayList<Cupon> aCupones) {
        cupones = aCupones;
    }

    /**
     * @return the ventas
     */
    public static ArrayList<Venta> getVentas() {
        return ventas;
    }

    /**
     * @param aVentas the ventas to set
     */
    public static void setVentas(ArrayList<Venta> aVentas) {
        ventas = aVentas;
    }

    /**
     * @return the librosv
     */
    public static ArrayList<LibroV> getLibrosv() {
        return librosv;
    }

    /**
     * @param aLibrosv the librosv to set
     */
    public static void setLibrosv(ArrayList<LibroV> aLibrosv) {
        librosv = aLibrosv;
    }

    /**
     * @return the usuarioActual
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param aUsuarioActual the usuarioActual to set
     */
    public static void setUsuarioActual(Usuario aUsuarioActual) {
        usuarioActual = aUsuarioActual;
    }

    /**
     * @return the direccionesv
     */
    public static ArrayList<DireccionV> getDireccionesv() {
        return direccionesv;
    }

    /**
     * @param aDireccionesv the direccionesv to set
     */
    public static void setDireccionesv(ArrayList<DireccionV> aDireccionesv) {
        direccionesv = aDireccionesv;
    }
}
