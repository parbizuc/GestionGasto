package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 27/03/19.
 */

public class Solicitud {
    String fechaInicio;
    String fechaFin;
    String descripción;
    String centro;
    String moneda;
    String importe;
    String fechaRegistro;
    String estado;
    String motivoRechazo;
    String nombreUsuario;
    String idUsuario;
    String id;

    public Solicitud() {

    }

    public Solicitud(String id, String fechaInicio, String fechaFin, String descripción, String centro, String moneda, String importe, String fechaRegistro, String estado, String motivoRechazo, String nombreUsuario, String idUsuario) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripción = descripción;
        this.centro = centro;
        this.moneda = moneda;
        this.importe = importe;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
    }

    public Solicitud(String fechaInicio, String fechaFin, String descripción, String centro, String moneda, String importe, String fechaRegistro, String motivoRechazo, String nombreUsuario, String idUsuario) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripción = descripción;
        this.centro = centro;
        this.moneda = moneda;
        this.importe = importe;
        this.fechaRegistro = fechaRegistro;
        this.motivoRechazo = motivoRechazo;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
