package com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno;

import android.content.Intent;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.MenuActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.altaAlumnos.AltaAlumnoActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.listaAlumnos.ListaAlumnosActivity;

import butterknife.OnClick;

public class AlumnosActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alumnos;
    }

    @OnClick(R.id.btn_ui_alumnos_alta)
    public void altaAlumnos(){
        startActivity(new Intent(this , AltaAlumnoActivity.class));
    }

    @OnClick(R.id.btn_ui_alumnos_lista)
    public void listaAlumnos(){ startActivity(new Intent(this , ListaAlumnosActivity.class));}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , MenuActivity.class ) );
    }
}
