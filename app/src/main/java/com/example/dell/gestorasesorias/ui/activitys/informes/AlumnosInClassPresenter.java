package com.example.dell.gestorasesorias.ui.activitys.informes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.AlumnoEnClase;

import java.util.ArrayList;

public class AlumnosInClassPresenter extends BasePresenter {

    ArrayList<AlumnoEnClase> alumnosEnClases;
    private Context context;
    private View view;

    public AlumnosInClassPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void getAlumnosEnClaseDB(){
        alumnosEnClases = new ArrayList<>();
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from alumnosclase";
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        alumnosEnClases.add(new AlumnoEnClase(c.getInt(0), c.getString(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4), c.getString(5) ));
                    } while (c.moveToNext());
                } else {
                    view.onNoDataDB();
                }
            }
            db.close();
            view.onDataSendSucces(alumnosEnClases);

        } catch (Exception e) {
            view.onErrorDB();
        }

    }

    public void deleteAlumnoDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "delete from alumnosclase where id=" + String.valueOf(id);
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();
            db.close();

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {

        //void guardarAlumnos(AlumnosResponse alumnosResponse);
        void onErrorDB();

        void onDataSendSucces(ArrayList<AlumnoEnClase> list);

        void onNoDataDB();


    }
}
