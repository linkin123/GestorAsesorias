package com.example.dell.gestorasesorias.ui.activitys.maestros.listaMaestros;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Dell on 10/06/2018.
 */

public class ListaMateriasAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ListaMateriasAdapter(FragmentManager fm , List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return "General          ";
            case 1 : return  "          Acredores";
            default: return "";
        }
    }
}
