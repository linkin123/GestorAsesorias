package com.example.dell.gestorasesorias.ui.activitys.ajustes.listaMaterias;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.data.models.Maestro;
import com.example.dell.gestorasesorias.data.models.Materia;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.AjustesPresenter;
import com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro.PerfilMaestroActivity;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.MaestrosGeneralAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Dell on 24/06/2018.
 */

public class AjustesListaMateriasActivity extends BaseActivity implements MateriasGeneralPresenter.View, ListaMateriasAdapter.onItemClickListener , ListaMateriasAdapter.onLongItemClickListener{

    @BindView(R.id.rv_materias_general)
    RecyclerView rvMateriasGeneral;

    private MateriasGeneralPresenter materiasGeneralPresenter;
    private ListaMateriasAdapter materiasAdapter;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Lista de materias");

        materiasAdapter = new ListaMateriasAdapter(this , new ArrayList<Materia>() , this , this );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMateriasGeneral.setLayoutManager(linearLayoutManager);
        rvMateriasGeneral.setAdapter(materiasAdapter);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        materiasGeneralPresenter = new MateriasGeneralPresenter(this , this);
        materiasGeneralPresenter.getMateriasDB();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ajustes_lista_materias;
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(this, "Error en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSendSucces(ArrayList<Materia> list) {
        materiasAdapter.updateList(list);
    }

    @Override
    public void onNoDataDB() {
        Toast.makeText(this, "No existen materias en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Materia materiaItem) {
/*        Intent i = new Intent(this, PerfilMaestroActivity.class);
        i.putExtra(PerfilMaestroActivity.ID, materiaItem.getId());
        startActivity(i);
*/

    }

    @Override
    public boolean onLongClick(int id) {
        materiasGeneralPresenter.deleteMateriaDB(id);
        return false;
    }
}
