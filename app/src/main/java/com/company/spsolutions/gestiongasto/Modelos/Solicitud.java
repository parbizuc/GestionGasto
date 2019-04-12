package com.company.spsolutions.gestiongasto.Modelos;

import java.io.Serializable;

/**
 * Created by coralRodriguez on 27/03/19.
 */

public class Solicitud implements Serializable {
    String fechaInicio;
    String fechaFin;
    String descripcion;
    String centro;
    String moneda;
    String importe; //montoSolicitado
    String fechaRegistro;
    String fechaEnviado;
    String estado;
    String motivo;
    String motivoRechazo;
    String nombreUsuario;
    String idUsuario;
    String id;
    String idEmpresa;
    String pais;

    public Solicitud() {

    }

    public Solicitud(String fechaInicio, String fechaFin, String descripcion, String centro, String motivo, String moneda, String importe, String fechaRegistro, String fechaEnviado, String estado, String motivoRechazo, String nombreUsuario, String idUsuario, String id, String idEmpresa, String pais) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.centro = centro;
        this.motivo = motivo;
        this.moneda = moneda;
        this.importe = importe;
        this.fechaRegistro = fechaRegistro;
        this.fechaEnviado = fechaEnviado;
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.pais = pais;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getFechaEnviado() {
        return fechaEnviado;
    }

    public void setFechaEnviado(String fechaEnviado) {
        this.fechaEnviado = fechaEnviado;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
