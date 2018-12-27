package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros.AltaMaestroPresenter;

public class AsesoriasPorTiempoPresenter {

    private Context context;
    private View view;


    public AsesoriasPorTiempoPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void setDataAlumnInClass(String alumno , String maestro , String materia){
        boolean valid = true;
        if (alumno.isEmpty()) {
            if (view != null) {
                view.onErrorNameAlumn();
            }
            valid = false;
        }
        if (maestro.isEmpty()) {
            if (view != null) {
                view.onErrorNameMaster();
            }
            valid = false;
        }
        if (materia.isEmpty()) {
            if (view != null) {
                view.onErrorMateria();
            }
            valid = false;
        }

        if (valid == true) {
            guardarDB( alumno, maestro , materia);
        }
    }

    private void guardarDB(String alumno, String maestro, String materia) {
        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "INSERT INTO ALUMNOSCLASE ( alumno , maestro , materia ) VALUES (? , ? , ?) ";
            SQLiteStatement insert = db.compileStatement(sql);
            insert.clearBindings();
            insert.bindString(1, alumno);
            insert.bindString(2, maestro);
            insert.bindString(3, materia);
            insert.executeInsert();
            db.close();
            view.onDataSuccessDB();
        } catch (Exception e) {
            view.onErrorDB();
        }

    }

    public interface View extends BasePresenter.View{

        void onErrorNameAlumn();

        void onErrorNameMaster();

        void onErrorMateria();

        void onErrorDB();

        void onDataSuccessDB();
    }

}
