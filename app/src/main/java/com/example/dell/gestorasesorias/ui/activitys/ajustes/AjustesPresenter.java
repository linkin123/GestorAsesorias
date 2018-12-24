package com.example.dell.gestorasesorias.ui.activitys.ajustes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dell on 24/06/2018.
 */

public class AjustesPresenter {

    private Context context;
    private View view;

    public AjustesPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    private void iniciarDatos(String nombreMateria , Bitmap bitmap) {

                /*procedimiento para guardar la foto*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            String sql = "INSERT INTO MATERIAS ( nombre ,  img ) VALUES (? , ? ) ";
            SQLiteStatement insert = db.compileStatement(sql);
            insert.clearBindings();
            insert.bindString(1, nombreMateria);
            insert.bindBlob(2, blob);
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
        } catch (
                Exception e)

        {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {

        void onErrorDB();

        void onDataSuccessDB();

    }

}
