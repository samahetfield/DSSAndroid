package com.example.sergio.dssandroid.servidor;

public class Farmacia {
    private String nombre;
    private float latitud;
    private float longitud;
    private int ID;

    public Farmacia(int id, String nombre, float latitud, float longitud) {
        this.ID = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }
}
