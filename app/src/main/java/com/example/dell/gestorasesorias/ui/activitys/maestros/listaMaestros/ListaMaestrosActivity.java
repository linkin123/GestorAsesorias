package com.example.dell.gestorasesorias.ui.activitys.maestros.listaMaestros;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.ListaMaestrosAcredoresFragment;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.ListaMaestrosGeneralFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Dell on 04/06/2018.
 */

public class ListaMaestrosActivity extends BaseActivity {

    @BindView(R.id.viewpager_maestros)
    ViewPager viewPager;

    @BindView(R.id.tab_maestros)
    TabLayout tabLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lista_maestros;
    }

    @Override
    protected void initView() {
        //inicializamos la vista desde el padre
        super.initView();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListaMaestrosGeneralFragment.newInstance());
        fragments.add(ListaMaestrosAcredoresFragment.newInstance());


        ListaMateriasAdapter pagerAdapter = new ListaMateriasAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


}
