package com.example.dell.gestorasesorias.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell on 03/11/2018.
 */

public class AlumnosResponse {

    @SerializedName("alumnos")
    private List<Alumno> alumno;

    public List<Alumno> getAlumno() {
        return alumno;
    }
}
