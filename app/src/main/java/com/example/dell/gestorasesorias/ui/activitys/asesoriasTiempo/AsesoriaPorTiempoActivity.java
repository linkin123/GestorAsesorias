package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AsesoriaPorTiempoActivity extends BaseActivity {

    private static final int ALARM_REQUEST_CODE  = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_asesoria_por_tiempo;
    }

    @BindView(R.id.profesor_name_time)
    EditText etProfesorNameTime;

    @BindView(R.id.alumn_name_time)
    EditText etAlumnoNameTime;

    @BindView(R.id.et_alumn_time_hour_exit)
    TextView tvTimeExit;

    private int i=0;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Alta de asesoria por tiempo");
    }

    @OnClick(R.id.btn_alumn_time_exit)
    public void setHour(){

        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hora, int minutes) {
                tvTimeExit.setText(hora+ ":" + minutes);

                int horaActual = c.get(Calendar.HOUR_OF_DAY);
                int minutosActuales = c.get(Calendar.MINUTE);
                int horaDuracion = hora-horaActual;
                int minutosDuracion = minutes -minutosActuales;
                horaDuracion=horaDuracion*3600;
                minutosDuracion=minutosDuracion*60;
                int duracionMiliseg = horaDuracion + minutosDuracion;

                Intent intent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                intent.putExtra("nombre" , etAlumnoNameTime.getText().toString());
                intent.putExtra("Maestro" , etProfesorNameTime.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 234324243, intent, 0);


                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);
                i++;
                //alarmManager.set(AlarmManager.RTC_WAKEUP, duracionMiliseg, pi);

                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                        + (duracionMiliseg * 1000), pendingIntent);
                Toast.makeText(getApplicationContext(), "Alarma puesta en " + duracionMiliseg + " segundos",
                        Toast.LENGTH_LONG).show();

            }
        }, hora , minutos, false);
        timePickerDialog.show();
    }

    private void setAlarm(int hora, int minutes) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "alumno :" + etAlumnoNameTime.getText().toString()
                        + "Maestro : "+ etProfesorNameTime.getText().toString())
                .putExtra(AlarmClock.EXTRA_HOUR, hora)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);

        if(i.resolveActivity(getPackageManager()) != null){
            startActivity(i);
        }
    }


}

