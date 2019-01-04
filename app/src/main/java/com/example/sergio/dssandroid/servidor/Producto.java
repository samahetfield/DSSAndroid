package com.example.sergio.dssandroid.servidor;

public class Producto {
    private int productoID;
    private String nombre;

    public Producto() {
        productoID = 0;
        nombre = "";
    }

    public Producto( int productoID, String nombre) {
        this.productoID = productoID;
        this.nombre = nombre;
    }

    public int getProductoID() {
        return productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ID = " + productoID + " Nombre = " + nombre;
    }
}
