package com.company.spsolutions.gestiongasto.Modelos;

/**
 * Created by coralRodriguez on 27/03/19.
 */
public class Usuario {
    String rol;
    String id;
    String nombre;
    String idEmpresa;
    String nombreEmpresa;
    String pais;
    private static Usuario ourInstance = null;

    public static Usuario getInstance() {
        if (ourInstance == null) {
            throw new AssertionError("Llama al init del Usuario");
        }
        return ourInstance;
    }

    private Usuario() {
    }

    private Usuario(String rol, String id, String nombre, String idEmpresa, String nombreEmpresa, String pais) {
        this.rol = rol;
        this.id = id;
        this.nombre = nombre;
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.pais = pais;
    }


    public synchronized static Usuario init(String rol, String id, String nombre, String idEmpresa, String nombreEmpresa, String pais) {
        if (ourInstance != null) {
            throw new AssertionError("Ya existe un usuario");
        }
        ourInstance = new Usuario(rol, id, nombre, idEmpresa, nombreEmpresa, pais);
        return ourInstance;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
