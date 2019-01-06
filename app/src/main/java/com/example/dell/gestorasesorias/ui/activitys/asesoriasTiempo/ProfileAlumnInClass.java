package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

import android.content.Intent;
import android.widget.TextView;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.MenuActivity;

import butterknife.BindView;

public class ProfileAlumnInClass extends BaseActivity {

    private String ID_ALUMNO = "numSocio";



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile_alumn_in_class;
    }

    protected void initView() {
        super.initView();
        ID_ALUMNO = getIntent().getStringExtra("numSocio");
//        setData();
    }
/*    private void setData() {
        tvIdAlumn2.setText(ID_ALUMNO);
    }
*/
    protected void initPresenter() {

    }

    @Override
     public void onBackPressed() {
        startActivity(new Intent(this , MenuActivity.class));
    }



}
