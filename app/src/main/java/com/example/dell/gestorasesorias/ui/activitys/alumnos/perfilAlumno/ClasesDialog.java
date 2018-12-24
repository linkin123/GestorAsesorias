package com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;

import butterknife.BindView;

public class ClasesDialog extends BaseActivity {

    @BindView(R.id.tv_captured_alumn)
    TextView tvCapturedAlumn;

    private String ID_ALUMNO = "numSocio";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clases_dialog;
    }

    @Override
    protected void initView() {
        super.initView();
        ID_ALUMNO = getIntent().getStringExtra("numSocio");
        setData();
    }
    private void setData() {
        tvCapturedAlumn.setText(ID_ALUMNO);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , AlumnosActivity.class));
    }


}
