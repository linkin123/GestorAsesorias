package com.example.dell.gestorasesorias.ui.activitys.ajustes;

import android.content.Intent;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.altaMaterias.AjustesMateriasActivity;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.listaMaterias.AjustesListaMateriasActivity;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.precios.PreciosActivity;

import butterknife.OnClick;

public class AjustesActivity extends BaseActivity implements  AjustesPresenter.View{

    private AjustesPresenter ajustesPresenter;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Ajustes");
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        ajustesPresenter = new AjustesPresenter(this , this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ajustes;
    }

    @OnClick(R.id.btn_ajustes_materias_alta)
    public void altaMateria(){
        startActivity( new Intent(this , AjustesMateriasActivity.class));
    }


    @OnClick(R.id.btn_ajustes_precios)
    public void precios(){
        startActivity( new Intent(this , PreciosActivity.class));
    }

    @OnClick(R.id.btn_ajustes_materias_modificar)
    public void modificarMateria(){
        startActivity( new Intent(this , AjustesListaMateriasActivity.class));
    }


    @Override
    public void onErrorDB() {

    }

    @Override
    public void onDataSuccessDB() {

    }

    /*insertar materias en la bdd antes de realizar cualquier operacion*/


}
