package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.ui.MenuActivity;

import java.nio.channels.InterruptedByTimeoutException;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Intent notificationIntent = new Intent(context , MenuActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        String nameAlumn = intent.getExtras().getString("nombre");
        String nameMaestro = intent.getExtras().getString("Maestro");
        int idVenta = intent.getExtras().getInt("id");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_alumnos)
                .setContentTitle("Gestor de asesorias")
                .setContentIntent(pendingIntent)
                .setContentText("alumno : " + nameAlumn)
                .setSubText( "Maestro : " + nameMaestro)
                .setSound(soundUri); //This sets the sound to play

        //Display notification
        notificationManager.notify(idVenta, mBuilder.build());

        Toast.makeText(context, "Alumno sale de clases !.",
                Toast.LENGTH_LONG).show();
        // Vibrate the mobile phone
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);


        //cachar id del alumno lanzar a la actividad del perfil con boton de finalizar clase

        Intent i = new Intent(context.getApplicationContext(), MenuActivity.class);
        i.putExtra("id", idVenta);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }
}
