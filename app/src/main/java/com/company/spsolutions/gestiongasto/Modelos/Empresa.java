package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 11/04/2019
 */
public class Empresa {
    String id;
    String moneda;
    String nombre;
    String prefijoPais;
    private static Empresa ourInstance = null;

    public static Empresa getInstance() {
        if (ourInstance == null) {
            throw new AssertionError("Llama al init de Empresa");
        }
        return ourInstance;
    }

    private Empresa() {
    }

    public Empresa(String id, String nombre, String moneda, String prefijoPais) {
        this.id = id;
        this.moneda = moneda;
        this.nombre = nombre;
        this.prefijoPais = prefijoPais;
    }

    public synchronized static Empresa init(String id, String nombre, String moneda, String prefijoPais) {
        if (ourInstance != null) {
            throw new AssertionError("Ya existe una empresa");
        }
        ourInstance = new Empresa(id, nombre, moneda, prefijoPais);
        return ourInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrefijoPais() {
        return prefijoPais;
    }

    public void setPrefijoPais(String prefijoPais) {
        this.prefijoPais = prefijoPais;
    }
}
