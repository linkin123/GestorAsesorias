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
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno.ClasesDialog;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {


    int i=0;

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, i, intent, 0);
        i++;
        String nameAlumn = intent.getExtras().getString("nombre");
        String nameMaestro = intent.getExtras().getString("Maestro");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_alumnos)
                .setContentTitle("Gestor de asesorias")
                .setContentIntent(pendingIntent)
                .setContentText("alumno: " + nameAlumn + "Maestro: " + nameMaestro)
                .setSound(soundUri); //This sets the sound to play

        //Display notification
        notificationManager.notify(0, mBuilder.build());

        Toast.makeText(context, "Don't panik but your time is up!!!!.",
                Toast.LENGTH_LONG).show();
        // Vibrate the mobile phone
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

    }

}
