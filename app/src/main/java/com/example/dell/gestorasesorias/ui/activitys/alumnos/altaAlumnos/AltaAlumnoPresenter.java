package com.example.dell.gestorasesorias.ui.activitys.alumnos.altaAlumnos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros.AltaMaestroPresenter;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dell on 12/06/2018.
 */

public class AltaAlumnoPresenter extends AppCompatActivity {

    private Context context;
    private View view;

    public AltaAlumnoPresenter(Context context, View view) {
        this.context = context;
        this.view = view;

    }


    public void setDataAlumno(String nombre, String nombrePadre, String telefono, String telefonoPadre , Bitmap bitmapFoto ) {
        boolean valid = true;
        if (nombre.isEmpty()) {
            if (view != null) {
                view.onErrorNombre();
            }
            valid = false;
        }
        if (nombrePadre.isEmpty()) {
            if (view != null) {
                view.onErrorNombrePadre();
            }
            valid = false;
        }
        if (telefono.isEmpty()) {
            if (view != null) {
                view.onErrorTelefono();
            }
            valid = false;
        }
        if (telefonoPadre.isEmpty()) {
            if (view != null) {
                view.onErrorTelefono();
            }
            valid = false;
        }
        if (bitmapFoto == null) {
            if (view != null) {
                view.onCamaraError();
            }
            valid = false;
        }


        if (valid == true) {
            guardarDB( nombre, nombrePadre, telefono, telefonoPadre , bitmapFoto);
        }
    }

    private void guardarDB(String nombre, String nombrePadre, String telefono, String telefonoPadre , Bitmap bitmap) {

        /*procedimiento para guardar la foto*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "INSERT INTO ALUMNOS ( nombre , nombrepadre , telefono , telefonopadre , img) VALUES (? , ? , ? , ? , ?) ";
            SQLiteStatement insert = db.compileStatement(sql);
            insert.clearBindings();
            insert.bindString(1 , nombre);
            insert.bindString(2 , nombrePadre);
            insert.bindString(3 , telefono);
            insert.bindString(4 , telefonoPadre);
            insert.bindBlob(5, blob);
            insert.executeInsert();
            db.close();

/*
            ContentValues c = new ContentValues();
            c.put("nombre", nombre);
            c.put("domicilio", domicilio);
            c.put("telefono", telefono);
            c.put("horario", horario);
            db.insert("MAESTROS", null, c);
            db.close();
*/
            view.onDataSuccessDB();
        } catch (Exception e) {
            view.onErrorDB();
        }

    }

    public interface View extends BasePresenter.View {
        void onErrorNombre();

        void onErrorNombrePadre();

        void onErrorTelefono();

        void onErrorTelefonoPadre();

        void onCamaraError();

        void onErrorDB();

        void onDataSuccessDB();

    }
}
