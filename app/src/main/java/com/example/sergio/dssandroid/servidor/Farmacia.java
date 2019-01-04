package com.example.sergio.dssandroid.servidor;

import org.json.JSONObject;

public class Farmacia extends JSONObject {
    private String nombre;
    private float latitud;
    private float longitud;
    private int ID;

    public Farmacia() {
        this.ID       = 0;
        this.nombre   = "";
        this.latitud  = 0;
        this.longitud = 0;
    }

    public Farmacia(int ID, String nombre, float latitud, float longitud) {
        this.ID       = ID;
        this.nombre   = nombre;
        this.latitud  = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "ID = " + ID + " Nombre = " + nombre + " Latitud = " + latitud + " Longitud = " + longitud;
    }
}
