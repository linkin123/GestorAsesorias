package com.example.dell.gestorasesorias.ui.fragments.listaAlumnos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.callbacks.AlumnosCallback;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;
import com.example.dell.gestorasesorias.data.models.Maestro;
import com.example.dell.gestorasesorias.data.models.error.Error;
import com.example.dell.gestorasesorias.interactors.AlumnosInteractor;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Dell on 12/06/2018.
 */

public class AlumnosGeneralPresenter extends BasePresenter /*implements AlumnosCallback*/{

    ArrayList<Alumno> alumnos;
    private Context context;
    private View view;


//    private AlumnosInteractor alumnosInteractor = new AlumnosInteractor(this);

    public AlumnosGeneralPresenter(Context context , View view) {
        this.context = context;
        this.view = view;
    }

    public void getAlumnosDB() {
        alumnos = new ArrayList<>();
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from alumnos";
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        byte[] blob = c.getBlob(5);
                        ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                        bitmap = BitmapFactory.decodeStream(bais);
                        alumnos.add(new Alumno(c.getInt(0), c.getString(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4), bitmap));
                    } while (c.moveToNext());
                } else {
                    view.onNoDataDB();
                }
            }
            db.close();
            view.onDataSendSucces(alumnos);

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public void deleteAlumnoDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "delete from alumnos where id=" + String.valueOf(id);
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            c.moveToFirst();
            db.close();

        } catch (Exception e) {
            view.onErrorDB();
        }
    }


/*    public void callAlumnos(){
        alumnosInteractor.callAlumnos();
    }
*/
    /*
    @Override
    public void onApiResponseError(Error error) {
        System.out.println("ocurrio un error en la respuesta del servicio ");
    }

    @Override
    public void onAlumnosResponse(AlumnosResponse alumnosResponse) {
        view.guardarAlumnos(alumnosResponse);
    }
    */

    public interface View extends BasePresenter.View {

        //void guardarAlumnos(AlumnosResponse alumnosResponse);
        void onErrorDB();

        void onDataSendSucces(ArrayList<Alumno> list);

        void onNoDataDB();


    }

}
