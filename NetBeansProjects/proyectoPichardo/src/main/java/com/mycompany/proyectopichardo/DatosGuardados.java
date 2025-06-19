/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author oem
 */
public class DatosGuardados implements Serializable{
    public ArrayList<Usuario> usuarios;
    public ArrayList<Libros> libros;
    public ArrayList<Cupon> cupones;
    public ArrayList<Venta> ventas;
    public ArrayList<LibroV> librosv;
    public ArrayList<DireccionV> direccionesv;
}
