package com.example.dell.gestorasesorias.data.models;

/**
 * Created by Dell on 04/07/2018.
 */

public class Precio {

    int id;
    String paquete;
    int precio;

    public Precio(int id, String paquete, int precio) {
        this.id = id;
        this.paquete = paquete;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
