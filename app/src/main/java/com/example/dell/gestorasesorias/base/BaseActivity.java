package com.example.dell.gestorasesorias.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Dell on 01/06/2018.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        bindView();
        initView();
        initPresenter();
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    private void bindView() {
        ButterKnife.bind(this);
    }

    protected void initView() {

    }


    protected void initPresenter() {

    }


}
