package com.example.dell.gestorasesorias.data.models;

public class AlumnoEnClase {

    int id;
    String alumno;
    String maestro;
    String materia;
    String horaSalida;
    String horaEntrada;

    public AlumnoEnClase(int id, String alumno, String maestro, String materia, String horaSalida, String horaEntrada) {
        this.id = id;
        this.alumno = alumno;
        this.maestro = maestro;
        this.materia = materia;
        this.horaSalida = horaSalida;
        this.horaEntrada = horaEntrada;
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

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}
