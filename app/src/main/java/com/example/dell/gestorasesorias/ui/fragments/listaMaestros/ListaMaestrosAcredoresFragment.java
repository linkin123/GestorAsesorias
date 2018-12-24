package com.example.dell.gestorasesorias.ui.fragments.listaMaestros;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseFragment;

/**
 * Created by Dell on 09/06/2018.
 */

public class ListaMaestrosAcredoresFragment extends BaseFragment{

    public static ListaMaestrosAcredoresFragment newInstance() {
        return new ListaMaestrosAcredoresFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_lista_maestros_acredores;
    }
}
