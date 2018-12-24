package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.widget.Toast;

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "Tu lógica de negocio irá aquí. En caso de requerir más de unos milisegundos, debería de la tarea a un servicio", Toast.LENGTH_LONG).show();

}
}
