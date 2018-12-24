package com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro;

import android.content.Intent;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.MenuActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros.AltaMaestroActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.listaMaestros.ListaMaestrosActivity;

import butterknife.OnClick;

public class MaestrosActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_maestros;
    }

    @OnClick(R.id.btn_ui_maestros_alta)
    public void altaMaestro(){
        startActivity(new Intent(this , AltaMaestroActivity.class ) );
    }

    @OnClick(R.id.btn_ui_maestros_lista)
    public void listaMaestros(){
        startActivity(new Intent(this , ListaMaestrosActivity.class ) );
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , MenuActivity.class ) );
    }
}
