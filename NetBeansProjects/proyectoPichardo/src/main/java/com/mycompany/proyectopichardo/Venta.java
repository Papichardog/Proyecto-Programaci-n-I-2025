/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

import java.util.Calendar;
import java.util.ArrayList;
/**
 *
 * @author oem
 */
public class Venta {
    public String nombre;
    public String nit;
    public String direccion;
    public double total;
    public String vendedor;
    public Calendar fecha;
    public ArrayList<LibroV> librosVendidos = new ArrayList<>();
    
}
