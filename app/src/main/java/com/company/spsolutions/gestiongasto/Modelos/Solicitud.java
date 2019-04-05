package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 27/03/19.
 */

public class Solicitud {
    String nombre;
    String puesto;
    String fecha;
    String dineroSolicitado;
    String estatus;

    public Solicitud() {

    }


    public Solicitud(String nombre, String puesto, String fecha, String dineroSolicitado, String estatus) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.fecha = fecha;
        this.dineroSolicitado = dineroSolicitado;
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDineroSolicitado() {
        return dineroSolicitado;
    }

    public void setDineroSolicitado(String dineroSolicitado) {
        this.dineroSolicitado = dineroSolicitado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
