package com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private String number;
    private String numberPadre;

    int id;
    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();

        if (intent.hasExtra(ID)) {
            id = intent.getIntExtra(ID, 0);
        }
        tvAlumnoTelefono.setOnClickListener((view) -> {
            number = tvAlumnoTelefono.getText().toString();
        });

        tvPadreTelefono.setOnClickListener((view) -> {
            numberPadre = tvPadreTelefono.getText().toString();
        });
    }

    public void validateCallPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    public void callPhone() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.replaceAll("-", ""))));

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
    public void onDataSendSucces(Alumno alumno) {

        civAlumno.setImageBitmap(alumno.getBitmap());
        tvNombreAlumno.setText(alumno.getAlumno());
        tvNombrePadre.setText(alumno.getPadreName());
        tvAlumnoTelefono.setText(alumno.getAlumnoPhone());
        tvPadreTelefono.setText(alumno.getPadrePhone());
    }



    @Override
    public void onNoDataDB() {
        Toast.makeText(getApplicationContext(), "No existe este registro en la base de datos", Toast.LENGTH_LONG).show();
    }
}
