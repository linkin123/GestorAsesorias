package com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.data.models.Maestro;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 11/06/2018.
 */

public class PerfilMaestroActivity extends BaseActivity implements PerfilMaestroPresenter.View {

    @BindView(R.id.civ_perfil_maestro)
    CircleImageView civMaestro;

    @BindView(R.id.tv_perfil_maestro_nombre)
    TextView tvNombre;

    @BindView(R.id.tv_perfil_maestro_domicilio)
    TextView tvDomicilio;

    @BindView(R.id.tv_perfil_maestro_telefono)
    TextView tvTelefono;

    @BindView(R.id.tv_perfil_maestro_descripcion)
    TextView tvDescripcion;


    @BindView(R.id.tv_perfil_maestro_horario)
    TextView tvHorario;


    private PerfilMaestroPresenter perfilMaestroPresenter;

    public static final String ID = "0";
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private String number;
    int id;

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();

        if (intent.hasExtra(ID)) {
            id = intent.getIntExtra(ID, 0);
        }

        tvTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = tvTelefono.getText().toString();

            }
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


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_perfil_maestro;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        perfilMaestroPresenter = new PerfilMaestroPresenter(this, this);
        perfilMaestroPresenter.getMaestroDB(id);
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "Error en la base de datos", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDataSendSucces(Maestro maestro) {
        civMaestro.setImageBitmap(maestro.getBitmap());
        tvNombre.setText(maestro.getNombre());
        tvDomicilio.setText(maestro.getDireccion());
        tvTelefono.setText(maestro.getTelefono());
        tvHorario.setText(maestro.getHorario());
        tvDescripcion.setText(maestro.getDescripcion());
    }

    @Override
    public void onNoDataDB() {
        Toast.makeText(getApplicationContext(), "No existe este registro en la base de datos", Toast.LENGTH_LONG).show();
    }

    public void callPhone() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.replaceAll("-", ""))));

    }



}
