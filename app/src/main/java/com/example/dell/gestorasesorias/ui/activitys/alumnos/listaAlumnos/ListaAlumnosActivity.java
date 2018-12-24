package com.example.dell.gestorasesorias.ui.activitys.alumnos.listaAlumnos;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.fragments.listaAlumnos.ListaAlumnosGeneralFragment;
import com.example.dell.gestorasesorias.ui.fragments.listaAlumnos.ListaAlumnosPaqueteFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Dell on 12/06/2018.
 */

public class ListaAlumnosActivity extends BaseActivity {

    @BindView(R.id.viewpager_alumnos)
    ViewPager viewPager;

    @BindView(R.id.tab_alumnos)
    TabLayout tabLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lista_alumnos;
    }

    @Override
    protected void initView() {
        //inicializamos la vista desde el padre
        super.initView();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListaAlumnosGeneralFragment.newInstance());
        fragments.add(ListaAlumnosPaqueteFragment.newInstance());


        PagerAdapterAlumnos pagerAdapter = new PagerAdapterAlumnos(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

}
