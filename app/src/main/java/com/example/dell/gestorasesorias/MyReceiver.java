package com.example.dell.gestorasesorias;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Dell on 25/11/2018.
 */

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Tu lógica de negocio irá aquí. En caso de requerir más de unos milisegundos, debería de la tarea a un servicio", Toast.LENGTH_LONG).show();
    }
}
