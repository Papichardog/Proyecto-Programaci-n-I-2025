/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;
import java.io.Serializable;

import java.util.Calendar;

/**
 *
 * @author oem
 */
public class Cupon implements Serializable{
     public String codigo;
      public String valor;
      public String tipo;
      public String fechavencimiento;
       public Calendar fecha;
       
    // Constructor vacío (si ya está, no lo repitas)
    public Cupon() {
        this.fecha = Calendar.getInstance();
    }

    // Constructor con parámetros
    public Cupon(String codigo, String valor, String tipo, String fechavencimiento) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipo = tipo;
        this.fechavencimiento = fechavencimiento;
        this.fecha = Calendar.getInstance();
    }
}
