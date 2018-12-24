package com.example.dell.gestorasesorias.ui.activitys.alumnos.paqueteAlumnos;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.altaAlumnos.AltaAlumnoPresenter;

import butterknife.BindView;

/**
 * Created by Dell on 15/06/2018.
 */

public class CompraPaqueteActivity extends BaseActivity{

    public static String EXTRA_ID = "id";
    private int idAlumno;
    public static  int nivel = 300;

   @BindView(R.id.tv_nombre_alumno)
    TextView nombreAlumno;

    @BindView(R.id.tv_diferencia)
    TextView tvDiferencia;

    @BindView(R.id.materias_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.materias_precio_recycler)
    RecyclerView recyclerViewPrecios;

    @BindView(R.id.spiner_materias)
    Spinner spinnerMateria;

    @BindView(R.id.spiner_horas)
    Spinner spinnerHoras;

    @BindView(R.id.et_alta_paquete_costo_total)
    EditText etCostoTotal;

    @BindView(R.id.et_alta_paquete_diferencia)
    EditText etDiferencia;

    @BindView(R.id.et_alta_paquete_registro_pago)
    EditText etRegistroPago;

    @BindView(R.id.rg_nivel)
    RadioGroup rgNivel;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_compra_paquete;
    }

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Compra de paquete");
        setdataSpinnersPaquete();
    }

    private void setdataSpinnersPaquete() {
        spinnerMateria.setAdapter(getDataAdapterPaquete(R.array.materia));
        spinnerHoras.setAdapter(getDataAdapterPaquete(R.array.horas));
        hideKeyboard(spinnerMateria);
        hideKeyboard(spinnerHoras);
    }
    private ArrayAdapter<CharSequence> getDataAdapterPaquete(@ArrayRes int array) {
        return ArrayAdapter.createFromResource(this, array, R.layout.item_view_spinners);

    }

    private void hideKeyboard(Spinner spinner) {
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

    }


    @Override
    protected void initPresenter() {
        super.initPresenter();
    }
}
