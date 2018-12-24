package com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.callbacks.AlumnosCallback;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;
import com.example.dell.gestorasesorias.data.models.error.Error;

import java.io.ByteArrayInputStream;

/**
 * Created by Dell on 12/06/2018.
 */

public class PerfilAlumnoPresenter implements AlumnosCallback{

    private Context context;
    private View view;

    public PerfilAlumnoPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void getAlumnoDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from alumnos where id=" + String.valueOf(id);
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            if (c.moveToFirst()) {
                byte[] blob = c.getBlob(5);
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                bitmap = BitmapFactory.decodeStream(bais);

            } else {
                view.onNoDataDB();
            }
            db.close();
//            view.onDataSendSucces(new Alumno(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), bitmap ));

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    @Override
    public void onApiResponseError(Error error) {
        System.out.println("ocurrio un error en la respuesta del servicio ");
    }

    @Override
    public void onAlumnosResponse(AlumnosResponse alumnosResponse) {
        view.onDataSendSucces(alumnosResponse);
    }

    public interface View extends BasePresenter.View {
        void onErrorDB();

        void onDataSendSucces(AlumnosResponse alumnosResponse);

        void onNoDataDB();
    }

}
