package com.example.dell.gestorasesorias.ui.activitys.alumnos.listaAlumnos;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.ListaMaestrosGeneralFragment;

import java.nio.BufferUnderflowException;

public class SelectionMasterActivity extends BaseActivity {


    public static final String ID_ALUMNO = "0";
    int idAlumno = 0;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_selection_master;
    }

    protected void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra(ID_ALUMNO)) {
            idAlumno = intent.getIntExtra(ID_ALUMNO, 0);
        }

        Fragment fragmentListMasters = new ListaMaestrosGeneralFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_a, fragmentListMasters).commit();

        Bundle data = new Bundle();
        data.putInt("idAlumno", idAlumno);
        fragmentListMasters.setArguments(data);

        /*        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_a , ListaMaestrosGeneralFragment.newInstance())
                .commit();
*/
    }



}
