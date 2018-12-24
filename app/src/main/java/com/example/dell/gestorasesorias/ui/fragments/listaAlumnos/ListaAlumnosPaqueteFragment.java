package com.example.dell.gestorasesorias.ui.fragments.listaAlumnos;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseFragment;

/**
 * Created by Dell on 12/06/2018.
 */

public class ListaAlumnosPaqueteFragment extends BaseFragment {

    public static ListaAlumnosPaqueteFragment newInstance() {
        return new ListaAlumnosPaqueteFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_lista_alumnos_paquete;
    }
}
