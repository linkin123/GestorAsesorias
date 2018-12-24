package com.example.dell.gestorasesorias.ui.activitys.ajustes.listaMaterias;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Maestro;
import com.example.dell.gestorasesorias.data.models.Materia;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.MaestrosGeneralPresenter;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Dell on 09/12/2018.
 */

public class MateriasGeneralPresenter extends BasePresenter{

    ArrayList<Materia> materias;
    private Context context;
    private View view;

    public MateriasGeneralPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void getMateriasDB(){
        materias = new ArrayList<>();
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from materias";
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        byte[] blob = c.getBlob(2);
                        ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                        bitmap = BitmapFactory.decodeStream(bais);
                        materias.add(new Materia(c.getInt(0), c.getString(1), bitmap));
                    } while (c.moveToNext());
                } else {
                    view.onNoDataDB();
                }
            }
            db.close();
            view.onDataSendSucces(materias);

        } catch (Exception e) {
            view.onErrorDB();
        }

    }

    public void deleteMateriaDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "delete from materias where id=" + String.valueOf(id);
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            c.moveToFirst();
            db.close();

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {
        void onErrorDB();

        void onDataSendSucces(ArrayList<Materia> list);

        void onNoDataDB();
    }

}
