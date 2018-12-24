package com.example.dell.gestorasesorias.ui.fragments.listaMaestros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Maestro;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Dell on 10/06/2018.
 */

public class MaestrosGeneralPresenter extends BasePresenter{

    ArrayList<Maestro> maestros;
    private Context context;
    private View view;

    public MaestrosGeneralPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void getMaestrosDB() {
        maestros = new ArrayList<>();
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from maestros";
            Cursor c = db.rawQuery(sql, null);
            Bitmap bitmap = null;
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        byte[] blob = c.getBlob(5);
                        ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                        bitmap = BitmapFactory.decodeStream(bais);
                        maestros.add(new Maestro(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), bitmap, c.getString(6)));
                    } while (c.moveToNext());
                } else {
                    view.onNoDataDB();
                }
            }
            db.close();
            view.onDataSendSucces(maestros);

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public void deleteMaestroDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "delete from maestros where id=" + String.valueOf(id);
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

        void onDataSendSucces(ArrayList<Maestro> list);

        void onNoDataDB();
    }

}
