package com.company.spsolutions.gestiongasto.Gastos;

/**
 * Created by coralRodriguez on 04/04/2019
 */
public class ItemGastos {
    String nombre;
    String fecha;
    String dinero;
    String imagen;

    public ItemGastos(String nombre, String dinero, String fecha, String imagen) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.fecha = fecha;
        this.imagen = imagen;
    }
}
