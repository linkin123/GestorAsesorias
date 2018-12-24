package com.example.dell.gestorasesorias.ui.activitys.ajustes.precios;

import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.data.models.Precio;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dell on 24/06/2018.
 */

public class PreciosActivity extends BaseActivity implements PreciosPresenter.View {

    private PreciosPresenter preciosPresenter;

    @BindView(R.id.et_precio_primaria_paquete)
    EditText precioPrimariaPaquete;
    @BindView(R.id.et_precio_secundaria_paquete)
    EditText precioSecundariaPaquete;
    @BindView(R.id.et_precio_preparatoria_paquete)
    EditText precioPreparatoriaPaquete;
    @BindView(R.id.et_precio_universidad_paquete)
    EditText precioUniversidadPaquete;

    @BindView(R.id.et_precio_primaria_hora)
    EditText precioPrimariaHora;
    @BindView(R.id.et_precio_secundaria_hora)
    EditText precioSecundariaHora;
    @BindView(R.id.et_precio_preparatoria_hora)
    EditText precioPreparatoriaHora;
    @BindView(R.id.et_precio_universidad_hora)
    EditText precioUniversidadHora;



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precios;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        preciosPresenter = new PreciosPresenter(getApplicationContext() , this);
        preciosPresenter.getPrecioDB();
    }


    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "aun no se han registrado precios", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSendSucces(ArrayList<Precio> list) {
        int i =0;
        if(list == null){
            precioPrimariaHora.setText("0");
            precioPrimariaPaquete.setText("0");
            precioSecundariaHora.setText("0");
            precioSecundariaPaquete.setText("0");
            precioPreparatoriaHora.setText("0");
            precioPreparatoriaPaquete.setText("0");
            precioUniversidadHora.setText("0");
            precioUniversidadPaquete.setText("0");

        }else{
            precioPrimariaHora.setText(list.get(0).getPrecio());
            precioPrimariaPaquete.setText(list.get(1).getPrecio());
            precioSecundariaHora.setText(list.get(2).getPrecio());
            precioSecundariaPaquete.setText(list.get(3).getPrecio());
            precioPreparatoriaHora.setText(list.get(4).getPrecio());
            precioPreparatoriaPaquete.setText(list.get(5).getPrecio());
            precioUniversidadHora.setText(list.get(6).getPrecio());
            precioUniversidadPaquete.setText(list.get(7).getPrecio());
        }

    }

    @OnClick(R.id.btn_ajustar_precios)
    public void AjustarPrecios(){

        ArrayList<Precio> precios;
        precios = new ArrayList<>();
        precios.add(new Precio(0 , "primariaHora" , Integer.parseInt(precioPrimariaHora.getText().toString()) ) );
        precios.add(new Precio(1 , "secundariaHora" , Integer.parseInt(precioSecundariaHora.getText().toString()) ) );
        precios.add(new Precio(2 , "preparatoriaHora" , Integer.parseInt(precioPreparatoriaHora.getText().toString()) ) );
        precios.add(new Precio(3 , "universidadHora" , Integer.parseInt(precioUniversidadHora.getText().toString()) ) );


        precios.add(new Precio(4 , "primariaPaquete" , Integer.parseInt(precioPrimariaPaquete.getText().toString()) ) );
        precios.add(new Precio(5 , "primariaPaquete" , Integer.parseInt(precioSecundariaPaquete.getText().toString()) ) );
        precios.add(new Precio(6 , "primariaPaquete" , Integer.parseInt(precioPreparatoriaPaquete.getText().toString()) ) );
        precios.add(new Precio(7 , "primariaPaquete" , Integer.parseInt(precioUniversidadPaquete.getText().toString()) ) );

        preciosPresenter.setPreciosDB( precios );
    }
    @Override
    public void onNoDataDB() {
        Toast.makeText(getApplicationContext(), "No existen maestros en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSuccessDB() {
        Toast.makeText(getApplicationContext(), "Precios modificados con exito", Toast.LENGTH_SHORT).show();
    }
}
