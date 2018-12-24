package com.example.dell.gestorasesorias.ui.activitys.alumnos.listaAlumnos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Dell on 12/06/2018.
 */

public class PagerAdapterAlumnos extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public PagerAdapterAlumnos(FragmentManager fm , List<Fragment> fragments) {
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
            case 1 : return  "          Paquete";
            default: return "";
        }
    }
}
