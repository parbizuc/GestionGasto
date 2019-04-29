package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 27/03/19.
 */
public class Gasto {
    String estado;
    String fechaGasto;
    String id;
    String idEmpresa;
    String monedaGasto;
    String montoGasto;
    String nombreEmpresa;
    String nombreProveedor;
    String ticket;
    String imagen;
    String idAnticipo;
    String montoAnticipo;
    String monedaAnticipo;
    String categoria;
    String comentario;
    String idUsuario;
    String nombreUsuario;
    Boolean esRecurrente;
    String periodoRecurrente;

    public Gasto() {

    }

    public Gasto(String estado, String fechaGasto, String id, String idEmpresa, String monedaGasto, String montoGasto, String nombreEmpresa, String nombreProveedor, String ticket) {
        this.estado = estado;
        this.fechaGasto = fechaGasto;
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.monedaGasto = monedaGasto;
        this.montoGasto = montoGasto;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreProveedor = nombreProveedor;
        this.ticket = ticket;
    }

    public Gasto(String estado, String fechaGasto, String id, String idEmpresa, String monedaGasto, String montoGasto, String nombreEmpresa, String nombreProveedor, String ticket, String imagen, String idAnticipo, String montoAnticipo, String monedaAnticipo, String categoria, String comentario, String idUsuario, String nombreUsuario, Boolean esRecurrente, String periodoRecurrente) {
        this.estado = estado;
        this.fechaGasto = fechaGasto;
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.monedaGasto = monedaGasto;
        this.montoGasto = montoGasto;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreProveedor = nombreProveedor;
        this.ticket = ticket;
        this.imagen = imagen;
        this.idAnticipo = idAnticipo;
        this.montoAnticipo = montoAnticipo;
        this.monedaAnticipo = monedaAnticipo;
        this.categoria = categoria;
        this.comentario = comentario;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.esRecurrente = esRecurrente;
        this.periodoRecurrente = periodoRecurrente;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdAnticipo() {
        return idAnticipo;
    }

    public void setIdAnticipo(String idAnticipo) {
        this.idAnticipo = idAnticipo;
    }

    public String getMontoAnticipo() {
        return montoAnticipo;
    }

    public void setMontoAnticipo(String montoAnticipo) {
        this.montoAnticipo = montoAnticipo;
    }

    public String getMonedaAnticipo() {
        return monedaAnticipo;
    }

    public void setMonedaAnticipo(String monedaAnticipo) {
        this.monedaAnticipo = monedaAnticipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public Boolean getEsRecurrente() {
        return esRecurrente;
    }

    public void setEsRecurrente(Boolean esRecurrente) {
        this.esRecurrente = esRecurrente;
    }

    public String getPeriodoRecurrente() {
        return periodoRecurrente;
    }

    public void setPeriodoRecurrente(String periodoRecurrente) {
        this.periodoRecurrente = periodoRecurrente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(String fechaGasto) {
        this.fechaGasto = fechaGasto;
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

    public String getMonedaGasto() {
        return monedaGasto;
    }

    public void setMonedaGasto(String monedaGasto) {
        this.monedaGasto = monedaGasto;
    }

    public String getMontoGasto() {
        return montoGasto;
    }

    public void setMontoGasto(String montoGasto) {
        this.montoGasto = montoGasto;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}

