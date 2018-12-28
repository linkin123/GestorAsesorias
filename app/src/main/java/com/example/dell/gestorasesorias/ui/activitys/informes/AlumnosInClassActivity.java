package com.example.dell.gestorasesorias.ui.activitys.informes;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnoEnClase;
import com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo.AlumnosInClassAdapter;
import com.example.dell.gestorasesorias.ui.fragments.listaAlumnos.AlumnosGeneralAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class AlumnosInClassActivity extends BaseActivity implements  AlumnosInClassAdapter.onItemClickListener , AlumnosInClassAdapter.onLongItemClickListener, AlumnosInClassPresenter.View {

    @BindView(R.id.rv_alumnos_in_class)
    RecyclerView rvAlumnosInClass;

    private AlumnosInClassPresenter alumnosInClassPresenter;
    private AlumnosInClassAdapter alumnosInClassAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alumns_in_class;
    }

    @Override
    protected void initView() {
        alumnosInClassAdapter = new AlumnosInClassAdapter(this, new ArrayList<AlumnoEnClase>(), this , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAlumnosInClass.setLayoutManager(linearLayoutManager);
        /*        rvMaestrosGeneral.setLayoutManager(new GridLayoutManager(getContext(), 3));*/
        rvAlumnosInClass.setAdapter(alumnosInClassAdapter);

    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        alumnosInClassPresenter = new AlumnosInClassPresenter(this , this);
        alumnosInClassPresenter.getAlumnosEnClaseDB();
        rvAlumnosInClass.setAdapter(alumnosInClassAdapter);
    }

    @Override
    public void onItemClick(AlumnoEnClase alumnoEnClase) {
    }

    @Override
    public boolean onLongClick(int id) {
        alumnosInClassPresenter.deleteAlumnoDB(id);
        return true;
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(this, "Error en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSendSucces(ArrayList<AlumnoEnClase> list) {
        alumnosInClassAdapter.updateList(list);
    }

    @Override
    public void onNoDataDB() {
        Toast.makeText(this, "No existen alumnos en la base de datos", Toast.LENGTH_SHORT).show();
    }
}
