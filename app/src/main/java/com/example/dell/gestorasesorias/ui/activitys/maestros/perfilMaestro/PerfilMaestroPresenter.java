package com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dell.gestorasesorias.base.BasePresenter;
import com.example.dell.gestorasesorias.data.database.BaseHelper;
import com.example.dell.gestorasesorias.data.models.Maestro;

import java.io.ByteArrayInputStream;

/**
 * Created by Dell on 11/06/2018.
 */

public class PerfilMaestroPresenter {

    private Context context;
    private View view;

    public PerfilMaestroPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void getMaestroDB(int id) {
        try {
            BaseHelper helper = new BaseHelper(context, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from maestros where id=" + String.valueOf(id);
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
            view.onDataSendSucces(new Maestro(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), bitmap , c.getString(6)));

        } catch (Exception e) {
            view.onErrorDB();
        }
    }

    public interface View extends BasePresenter.View {
        void onErrorDB();

        void onDataSendSucces(Maestro maestro);

        void onNoDataDB();
    }


}
