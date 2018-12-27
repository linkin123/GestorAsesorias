package com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dell on 04/06/2018.
 */

public class AltaMaestroPresenter  {

    private Context context;
    private View view;

    public AltaMaestroPresenter(Context context, View view) {
        this.context = context;
        this.view = view;

    }


    public void setDataMaestro(String nombre, String domicilio, String telefono, String horario , Bitmap bitmapFoto , String descripcion) {
        boolean valid = true;
        if (nombre.isEmpty()) {
            if (view != null) {
                view.onErrorNombre();
            }
            valid = false;
        }
        if (domicilio.isEmpty()) {
            if (view != null) {
                view.onErrorDomicilio();
            }
            valid = false;
        }
        if (telefono.isEmpty()) {
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
            guardarDB( nombre, domicilio, telefono, horario , bitmapFoto , descripcion);
        }
    }

    private void guardarDB(String nombre, String domicilio, String telefono, String horario , Bitmap bitmap , String descripcion) {

        /*procedimiento para guardar la foto*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "INSERT INTO MAESTROS ( nombre , domicilio , telefono , horario , img , descripcion) VALUES (? , ? , ? , ? , ? , ?) ";
            SQLiteStatement insert = db.compileStatement(sql);
            insert.clearBindings();
            insert.bindString(1 , nombre);
            insert.bindString(2 , domicilio);
            insert.bindString(3 , telefono);
            insert.bindString(4 , horario);
            insert.bindBlob(5, blob);
            insert.bindString(6 , descripcion);
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

        void onErrorDomicilio();

        void onErrorTelefono();

        void onCamaraError();

        void onErrorDB();

        void onDataSuccessDB();

    }

}
