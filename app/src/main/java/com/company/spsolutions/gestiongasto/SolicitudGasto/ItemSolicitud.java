package com.company.spsolutions.gestiongasto.SolicitudGasto;

/**
 * Created by coralRodriguez on 04/04/2019
 */
public class ItemSolicitud {
    String nombre;
    String puesto;
    String fecha;
    String dineroSolicitado;
    String estatus;

    public ItemSolicitud(String nombre, String puesto, String fecha, String dineroSolicitado) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.fecha = fecha;
        this.dineroSolicitado = dineroSolicitado;
    }

    public ItemSolicitud(String nombre, String puesto, String fecha, String dineroSolicitado, String estatus) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.fecha = fecha;
        this.dineroSolicitado = dineroSolicitado;
        this.estatus = estatus;
    }
}
