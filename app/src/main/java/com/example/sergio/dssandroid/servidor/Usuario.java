package com.example.sergio.dssandroid.servidor;

import org.json.JSONObject;

public class Usuario extends JSONObject{
    private String nombre;
    private String nombreUsuario;
    private String correo;
    private String contrasena;

    public Usuario() {
        this.nombre        = "";
        this.nombreUsuario = "";
        this.correo        = "";
        this.contrasena    = "";
    }

    public Usuario(String nombre, String nombreUsuario, String correo, String contrasena) {
        this.nombre        = nombre;
        this.nombreUsuario = nombreUsuario;
        this.correo        = correo;
        this.contrasena    = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String toString() {
        return "Nombre= " + nombre + " UserName= " + nombreUsuario + " Correo = " + correo;
    }


}
