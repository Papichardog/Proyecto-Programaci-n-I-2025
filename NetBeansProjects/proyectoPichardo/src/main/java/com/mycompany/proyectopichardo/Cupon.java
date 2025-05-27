/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectopichardo;

/**
 *
 * @author oem
 */
public class Cupon {
     public String codigo;
      public String valor;
      public String tipo;
      public String fechavencimiento;
    // Constructor vacío (si ya está, no lo repitas)
    public Cupon() {}

    // Constructor con parámetros
    public Cupon(String codigo, String valor, String tipo, String fechavencimiento) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipo = tipo;
        this.fechavencimiento = fechavencimiento;
    }
}
