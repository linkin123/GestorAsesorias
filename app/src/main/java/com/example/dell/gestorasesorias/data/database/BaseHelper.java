package com.example.dell.gestorasesorias.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 06/06/2018.
 */

public class BaseHelper  extends SQLiteOpenHelper {

    /******************tabla maestros***********************************/
    String tablaMaestros = "CREATE TABLE " +
            "MAESTROS(ID INTEGER PRIMARY KEY , " +
            "NOMBRE TEXT, " +
            "DOMICILIO TEXT, " +
            "TELEFONO INTEGER, " +
            "HORARIO TEXT," +
            "IMG BLOB, " +
            "DESCRIPCION TEXT )";

    /******************tabla alumnos***********************************/
    String tablaAlumnos = "CREATE TABLE " +
            "ALUMNOS(ID INTEGER PRIMARY KEY , " +
            "NOMBRE TEXT, " +
            "NOMBREPADRE TEXT, " +
            "TELEFONO TEXT, " +
            "TELEFONOPADRE TEXT ," +
            "IMG BLOB )";

    /******************tabla precios***********************************/
    String tablaPrecios = "CREATE TABLE " +
            "PRECIOS(ID INTEGER PRIMARY KEY , " +
            "PAQUETE TEXT, " +
            "PRECIO INT )";

    /******************tabla materias***********************************/
    String tablaMaterias = "CREATE TABLE " +
            "MATERIAS(ID INTEGER PRIMARY KEY , " +
            "NOMBRE TEXT, " +
            "IMG BLOB )";

    /******************tabla alumnos en clase***********************************/
    String tablaAlumnosEnClase = "CREATE TABLE " +
            "ALUMNOSCLASE(ID INTEGER PRIMARY KEY , " +
            "NOMBRE TEXT, " +
            "PROFESPR TEXT )";


    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablaMaestros);
        db.execSQL(tablaAlumnos);
        db.execSQL(tablaPrecios);
        db.execSQL(tablaMaterias);
        db.execSQL(tablaAlumnosEnClase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

//      eliminamos y creamos tabla maestros
        db.execSQL("drop table maestros");
        db.execSQL(tablaMaestros);
//      eliminamos y creamos tabla alumnos
        db.execSQL("drop table alumnos");
        db.execSQL(tablaAlumnos);
//      eliminamos y creamos tabla precios
        db.execSQL("drop table precios");
        db.execSQL(tablaPrecios);
//      eliminamos y creamos tabla materias
        db.execSQL("drop table materias");
        db.execSQL(tablaMaterias);
//      eliminamos y creamos tabla materias
        db.execSQL("drop table alumnosenclase");
        db.execSQL(tablaAlumnosEnClase);

    }
}
