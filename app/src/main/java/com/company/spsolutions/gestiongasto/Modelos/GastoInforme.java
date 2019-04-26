package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 23/04/2019
 */
public class GastoInforme {
    String id;
    String idInforme;
    String idGasto;
    String estado;

    public GastoInforme() {
    }

    public GastoInforme(String id, String idInforme, String idGasto, String estado) {
        this.id = id;
        this.idInforme = idInforme;
        this.idGasto = idGasto;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(String idInforme) {
        this.idInforme = idInforme;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
