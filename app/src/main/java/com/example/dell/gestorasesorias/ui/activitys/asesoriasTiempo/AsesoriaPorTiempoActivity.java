package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.widget.TextView;
import android.widget.TimePicker;

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
    TextView etProfesorNameTime;

    @BindView(R.id.alumn_name_time)
    TextView etAlumnoNameTime;

    @BindView(R.id.et_alumn_time_hour_exit)
    TextView tvTimeExit;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Alta de asesoria por tiempo");
    }

    @OnClick(R.id.btn_alumn_time_exit)
    public void setHour(){
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");


        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hora, int minutes) {
                tvTimeExit.setText(hora+ ":" + minutes);
                setAlarm( hora , minutes);
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

