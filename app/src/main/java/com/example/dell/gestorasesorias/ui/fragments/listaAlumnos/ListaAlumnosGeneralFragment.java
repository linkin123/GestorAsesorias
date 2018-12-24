package com.example.dell.gestorasesorias.ui.fragments.listaAlumnos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseFragment;
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno.PerfilAlumnoActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Dell on 12/06/2018.
 */

public class ListaAlumnosGeneralFragment extends BaseFragment implements AlumnosGeneralPresenter.View, AlumnosGeneralAdapter.onItemClickListener, AlumnosGeneralAdapter.onLongItemClickListener {

    @BindView(R.id.rv_alumnos_general)
    RecyclerView rvAlumnosGeneral;

    private AlumnosGeneralPresenter alumnosGeneralPresenter;
    private AlumnosGeneralAdapter alumnosGeneralAdapter;

    public static ListaAlumnosGeneralFragment newInstance() {
        return new ListaAlumnosGeneralFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_lista_alumnos_general;
    }


    @Override
    protected void initFragment(@NonNull View view) {
        super.initFragment(view);

        alumnosGeneralAdapter = new AlumnosGeneralAdapter(getContext(), new ArrayList<Alumno>(), this , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAlumnosGeneral.setLayoutManager(linearLayoutManager);
/*        rvMaestrosGeneral.setLayoutManager(new GridLayoutManager(getContext(), 3));*/
        rvAlumnosGeneral.setAdapter(alumnosGeneralAdapter);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        alumnosGeneralPresenter = new AlumnosGeneralPresenter( getContext() ,this );
//        alumnosGeneralPresenter.callAlumnos();
        alumnosGeneralPresenter.getAlumnosDB();
        rvAlumnosGeneral.setAdapter(alumnosGeneralAdapter);
    }


    @Override
    public void onItemClick(Alumno alumno) {
        Intent i = new Intent(getContext() , PerfilAlumnoActivity.class);
        i.putExtra(PerfilAlumnoActivity.ID , alumno.getId());
        startActivity(i);
    }

    @Override
    public boolean onLongClick(int id) {
        alumnosGeneralPresenter.deleteAlumnoDB(id);
        return true;
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getContext(), "Error en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSendSucces(ArrayList<Alumno> list) {
        alumnosGeneralAdapter.updateList(list);
    }

    @Override
    public void onNoDataDB() {
        Toast.makeText(getContext(), "No existen alumnos en la base de datos", Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public void guardarAlumnos(AlumnosResponse alumnosResponse) {
        alumnosGeneralAdapter.updateList(alumnosResponse.getAlumno());
    }

    */
}
