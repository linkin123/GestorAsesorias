package com.example.dell.gestorasesorias.ui;

import android.content.Intent;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno.AlumnosActivity;
import com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo.AsesoriaPorTiempoActivity;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.AjustesActivity;
import com.example.dell.gestorasesorias.ui.activitys.informes.AlumnosInClassActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro.MaestrosActivity;
import com.example.dell.gestorasesorias.ui.activitys.reportes.ReportesActivity;

import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_menu;
    }


    @OnClick(R.id.ib_alumno)
    public void onKlickAlumnos(){
        startActivity( new Intent(this , AlumnosActivity.class) );
    }

    @OnClick(R.id.ib_maestro)
    public void onKlickMaestro(){
        startActivity( new Intent(this , MaestrosActivity.class) );
    }

    @OnClick(R.id.ib_ajustes)
    public void onKlickAjustes(){
        startActivity( new Intent(this , AjustesActivity.class) );
    }

    @OnClick(R.id.ib_asesoria_tiempo)
    public void onKlickAsesoriasPorTiempo(){
        startActivity( new Intent(this , AsesoriaPorTiempoActivity.class) );
    }

    @OnClick(R.id.ib_reportes)
    public void onKlickReportes(){
        startActivity( new Intent(this , ReportesActivity.class) );
    }

    @OnClick(R.id.ib_informes)
    public void onKlickInformes(){
        startActivity( new Intent(this , AlumnosInClassActivity.class) );
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
