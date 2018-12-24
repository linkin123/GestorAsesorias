package com.example.dell.gestorasesorias.ui.activitys.ajustes.precios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Precio;

import java.util.ArrayList;

/**
 * Created by Dell on 04/07/2018.
 */

public class PreciosPresenter {

    ArrayList<Precio> precios;
    private Context context;
    private View view;

    public PreciosPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void setPreciosDB(ArrayList<Precio> precios) {

        BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {

            for (Precio p : precios) {
                String sql = "INSERT INTO PRECIOS ( paquete , precio ) VALUES (? , ? , ? ) ";
                SQLiteStatement insert = db.compileStatement(sql);
                insert.clearBindings();
                insert.bindString(1, p.getPaquete());
                insert.bindString(2, String.valueOf(p.getPrecio()));
                insert.executeInsert();
                db.close();

            }
            view.onDataSuccessDB();
        } catch (Exception e) {
            view.onErrorDB();
        }


    }

    public void getPrecioDB() {
        precios = new ArrayList<>();
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from precios";
            Cursor c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        precios.add(new Precio(c.getInt(0), c.getString(1), c.getInt(2)));
                    } while (c.moveToNext());
                } else {
                    view.onNoDataDB();
                }
            }
            db.close();
            view.onDataSendSucces(precios);

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {
        void onErrorDB();

        void onDataSendSucces(ArrayList<Precio> list);

        void onNoDataDB();

        void onDataSuccessDB();
    }

}
