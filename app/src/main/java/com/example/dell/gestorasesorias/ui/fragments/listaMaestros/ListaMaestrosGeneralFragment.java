package com.example.dell.gestorasesorias.ui.fragments.listaMaestros;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseFragment;
import com.example.dell.gestorasesorias.data.models.Maestro;
import com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro.PerfilMaestroActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Dell on 10/06/2018.
 */

public class ListaMaestrosGeneralFragment extends BaseFragment implements MaestrosGeneralPresenter.View, MaestrosGeneralAdapter.onItemClickListener , MaestrosGeneralAdapter.onLongItemClickListener {

    @BindView(R.id.rv_maestros_general)
    RecyclerView rvMaestrosGeneral;

    private MaestrosGeneralPresenter maestrosGeneralPresenter;
    private MaestrosGeneralAdapter maestrosGeneralAdapter;

    public static ListaMaestrosGeneralFragment newInstance() {
        return new ListaMaestrosGeneralFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_lista_maestros_general;
    }


    @Override
    protected void initFragment(@NonNull View view) {
        super.initFragment(view);

        maestrosGeneralAdapter = new MaestrosGeneralAdapter(getContext(), new ArrayList<Maestro>(), this , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvMaestrosGeneral.setLayoutManager(linearLayoutManager);
/*        rvMaestrosGeneral.setLayoutManager(new GridLayoutManager(getContext(), 3));*/
        rvMaestrosGeneral.setAdapter(maestrosGeneralAdapter);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        maestrosGeneralPresenter = new MaestrosGeneralPresenter(getContext(), this);
        maestrosGeneralPresenter.getMaestrosDB();
    }



    @Override
    public void onErrorDB() {
        Toast.makeText(getContext(), "Error en la base de datos", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDataSendSucces(ArrayList<Maestro> list) {
        maestrosGeneralAdapter.updateList(list);

    }

    @Override
    public void onNoDataDB() {
        Toast.makeText(getContext(), "No existen maestros en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Maestro maestroItem) {
        Intent i = new Intent(getContext(), PerfilMaestroActivity.class);
        i.putExtra(PerfilMaestroActivity.ID, maestroItem.getId());
        startActivity(i);
    }


    @Override
    public boolean onLongClick(int id) {
        maestrosGeneralPresenter.deleteMaestroDB(id);
        return true;
    }
}
