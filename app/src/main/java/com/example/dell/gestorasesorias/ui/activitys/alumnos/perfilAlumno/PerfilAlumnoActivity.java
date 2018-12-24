package com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.paqueteAlumnos.CompraPaqueteActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dell on 12/06/2018.
 */

public class PerfilAlumnoActivity extends BaseActivity implements PerfilAlumnoPresenter.View{

    @BindView(R.id.civ_perfil_alumno)
    ImageView civAlumno;

    @BindView(R.id.tv_perfil_nombre_alumno)
    TextView tvNombreAlumno;

    @BindView(R.id.tv_perfil_nombre_padre)
    TextView tvNombrePadre;

    @BindView(R.id.tv_perfil_alumno_telefono)
    TextView tvAlumnoTelefono;

    @BindView(R.id.tv_perfil_padre_telefono)
    TextView tvPadreTelefono;

    private PerfilAlumnoPresenter perfilAlumnoPresenter;

    public static final String ID = "0";
    int id;
    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();

        if (intent.hasExtra(ID)) {
            id = intent.getIntExtra(ID, 0);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_perfil_alumno;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        perfilAlumnoPresenter = new PerfilAlumnoPresenter(this , this);
        perfilAlumnoPresenter.getAlumnoDB(id);
    }

    @OnClick(R.id.btn_alumno_comprar)
    public void comprarPaquete(){
        startActivity( new Intent(this , CompraPaqueteActivity.class));
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "Error en la base de datos", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataSendSucces(AlumnosResponse alumnosResponse) {
/*
        alumnosResponse.
        civAlumno.setImageBitmap(alumnosResponse.getBitmap());
        tvNombreAlumno.setText(alumnosResponse.getNombre());
        tvNombrePadre.setText(alumnosResponse.getNombrePadre());
        tvAlumnoTelefono.setText(alumnosResponse.getTelefono());
        tvPadreTelefono.setText(alumnosResponse.get);
*/    }



    @Override
    public void onNoDataDB() {
        Toast.makeText(getApplicationContext(), "No existe este registro en la base de datos", Toast.LENGTH_LONG).show();
    }
}
