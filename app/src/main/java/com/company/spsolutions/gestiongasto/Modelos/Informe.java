package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class Informe {
    String id;
    String idEmpresa;
    String nombreEmpresa;
    String titulo;
    String idAnticipo;
    String fechaInicio;
    String fechaFin;
    String comentario;
    String montoInforme;
    String monedaInforme;
    String idUsuario;
    String nombreUsuario;
    String fechaRegistro;
    String fechaEnvio;
    String estado;

    public Informe() {
    }

    public Informe(String id, String idEmpresa, String nombreEmpresa, String titulo, String idAnticipo, String fechaInicio, String fechaFin, String comentario, String montoInforme, String monedaInforme, String idUsuario, String nombreUsuario, String fechaRegistro, String fechaEnvio, String estado) {
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.titulo = titulo;
        this.idAnticipo = idAnticipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.comentario = comentario;
        this.montoInforme = montoInforme;
        this.monedaInforme = monedaInforme;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.fechaRegistro = fechaRegistro;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdAnticipo() {
        return idAnticipo;
    }

    public void setIdAnticipo(String idAnticipo) {
        this.idAnticipo = idAnticipo;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getMontoInforme() {
        return montoInforme;
    }

    public void setMontoInforme(String montoInforme) {
        this.montoInforme = montoInforme;
    }

    public String getMonedaInforme() {
        return monedaInforme;
    }

    public void setMonedaInforme(String monedaInforme) {
        this.monedaInforme = monedaInforme;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
