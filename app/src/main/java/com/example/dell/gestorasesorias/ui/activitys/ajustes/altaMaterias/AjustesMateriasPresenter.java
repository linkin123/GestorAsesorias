package com.example.dell.gestorasesorias.ui.activitys.ajustes.altaMaterias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dell on 08/12/2018.
 */

public class AjustesMateriasPresenter {
    private Context context;
    private View view;

    public AjustesMateriasPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void setDataMateria(String nombre,  Bitmap bitmapFoto) {
        boolean valid = true;
        if (nombre.isEmpty()) {
            if (view != null) {
                view.onErrorNombre();
            }
            valid = false;
        }
        if (bitmapFoto ==null) {
            if (view != null) {
                view.onCamaraError();
            }
            valid = false;
        }

        if (valid == true) {
            guardarDB( nombre,  bitmapFoto);
        }

    }
    private void guardarDB(String nombre,  Bitmap bitmap) {
                /*procedimiento para guardar la foto*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] blob = baos.toByteArray();

        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "INSERT INTO MATERIAS ( nombre, img) VALUES (?,?) ";
            SQLiteStatement insert = db.compileStatement(sql);
            insert.clearBindings();
            insert.bindString(1, nombre);
            insert.bindBlob(2, blob);
            insert.executeInsert();
            db.close();

            view.onDataSuccessDB();
        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {
        void onErrorNombre();

        void onErrorDB();

        void onCamaraError();

        void onDataSuccessDB();

    }


}
