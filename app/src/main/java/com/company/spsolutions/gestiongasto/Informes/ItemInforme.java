package com.company.spsolutions.gestiongasto.Informes;

/**
 * Created by coralRodriguez on 04/04/2019
 */
public class ItemInforme {
    String nombre;
    String fechas;
    String dineroTotal;
    String estatus;

    public ItemInforme(String nombre, String fechas, String dineroTotal) {
        this.nombre = nombre;
        this.fechas = fechas;
        this.dineroTotal = dineroTotal;
    }

    public ItemInforme(String nombre, String fechas, String dineroTotal, String estatus) {
        this.nombre = nombre;
        this.fechas = fechas;
        this.dineroTotal = dineroTotal;
        this.estatus = estatus;
    }
}
