package com.example.sergio.dssandroid.servidor;

public class Order {
    private int orderID;
    private Producto producto;
    private Usuario usuario;

    public Order() {
        this.orderID  = orderID;
        this.producto = new Producto();
        this.usuario  = new Usuario();
    }

    public Order(int orderID, Producto producto, Usuario usuario) {
        this.orderID  = orderID;
        this.producto = producto;
        this.usuario  = usuario;
    }

    public Order(int orderID, int productoID, String username) {
        this.orderID  = orderID;
        this.producto = new Producto(productoID,"");
        this.usuario  = new Usuario("",username,"","");
    }

    public int getOrderID() {
        return orderID;
    }

    public Producto getProducto() {
        return producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "ID = " + orderID + " Usuario = " + usuario.getNombreUsuario() + " Producto " + producto.getProductoID();
    }

}
