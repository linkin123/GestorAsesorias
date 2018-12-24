package com.example.dell.gestorasesorias.data.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 12/06/2018.
 */

public class Alumno {

    int id;
    String alumno;
    String alumnoPhone;
    String padreName;
    String padrePhone;
    Bitmap bitmap;

    public Alumno(int id, String alumno, String alumnoPhone, String padreName, String padrePhone, Bitmap bitmap) {
        this.id = id;
        this.alumno = alumno;
        this.alumnoPhone = alumnoPhone;
        this.padreName = padreName;
        this.padrePhone = padrePhone;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getAlumnoPhone() {
        return alumnoPhone;
    }

    public void setAlumnoPhone(String alumnoPhone) {
        this.alumnoPhone = alumnoPhone;
    }

    public String getPadreName() {
        return padreName;
    }

    public void setPadreName(String padreName) {
        this.padreName = padreName;
    }

    public String getPadrePhone() {
        return padrePhone;
    }

    public void setPadrePhone(String padrePhone) {
        this.padrePhone = padrePhone;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
