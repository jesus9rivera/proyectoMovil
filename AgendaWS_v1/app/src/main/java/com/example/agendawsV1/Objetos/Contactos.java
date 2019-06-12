package com.example.agendawsV1.Objetos;

import java.io.Serializable;

public class Contactos implements Serializable {

    private int _ID;
    private String nombre;
    private String telefono1;
    private String telefono2;
    private String direccion;
    private String notas;
    private int favorite;
    private String idMovil;

    public Contactos() {

    }
    public Contactos(String nombre, String telefono1, String telefono2, String
            direccion, String notas, int favorite, String idMovil) {
        this.nombre = nombre;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
        this.notas = notas;
        this.favorite = favorite;
        this.idMovil=idMovil;
    }

    public int get_ID() {
        return _ID;
    }
    public void set_ID(int _ID) {
        this._ID = _ID;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono1() {
        return telefono1;
    }
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }
    public String getTelefono2() {
        return telefono2;
    }
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }
    public int getFavorite() {
        return favorite;
    }
    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
    public String getIdMovil() {
        return idMovil;
    }
    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

}
