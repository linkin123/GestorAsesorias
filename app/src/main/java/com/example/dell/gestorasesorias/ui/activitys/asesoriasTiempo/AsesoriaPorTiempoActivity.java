package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.MenuActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AsesoriaPorTiempoActivity extends BaseActivity {

    private static final int ALARM_REQUEST_CODE  = 1;
    private int mHora;
    private int mMinutos;

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hora, int minutos) {
                tvTimeExit.setText(hora+ ":" + minutos);
                    mHora = hora;
                    mMinutos = minutos;

            }
        }, hora , minutos, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.btn_alumn_time_start)
    public void initClass(){
        final Calendar c = Calendar.getInstance();
        int horaActual = c.get(Calendar.HOUR_OF_DAY);
        int minutosActuales = c.get(Calendar.MINUTE);
        int horaDuracion = mHora-horaActual;
        int minutosDuracion = mMinutos -minutosActuales;
        horaDuracion=horaDuracion*3600;
        minutosDuracion=minutosDuracion*60;
        int duracionMiliseg = horaDuracion + minutosDuracion;

        Intent intent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
        intent.putExtra("nombre" , etAlumnoNameTime.getText().toString());
        intent.putExtra("Maestro" , etProfesorNameTime.getText().toString());
        intent.putExtra("id" ,  (int) System.currentTimeMillis());
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), _id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (duracionMiliseg * 1000), pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarma puesta en " + duracionMiliseg + " segundos",
                Toast.LENGTH_LONG).show();

        startActivity(new Intent(this , AsesoriaPorTiempoActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , MenuActivity.class ) );
    }


}

