package com.example.dell.gestorasesorias.data.models;

import android.graphics.Bitmap;

/**
 * Created by Dell on 09/12/2018.
 */

public class Materia {

    int id;
    String nombre;
    Bitmap bitmap;


    public Materia(int id, String nombre, Bitmap bitmap) {
        this.id = id;
        this.nombre = nombre;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
