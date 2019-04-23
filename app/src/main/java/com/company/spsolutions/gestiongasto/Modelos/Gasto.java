package com.company.spsolutions.gestiongasto.Modelos;
/**
 * Created by coralRodriguez on 27/03/19.
 */
public class Gasto {
    String nombre;

    public Gasto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
