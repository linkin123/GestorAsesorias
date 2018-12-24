package com.example.dell.gestorasesorias.base;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Dell on 01/06/2018.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view);
        initPresenter();

    }

    protected void initFragment(@NonNull View view) {

    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void initPresenter() {

    }

    protected ActionBar getSupportActionBar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    protected void setSupportActionBarOnFragment(@NonNull Toolbar toolbar) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    protected BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    protected FragmentManager getActivitySupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    protected void onReplaceTransaction(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getActivitySupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
                .commit();
    }

    protected void onReplaceTransactionWithBackStack(@IdRes int containerViewId,
                                                     @NonNull Fragment fragment) {
        getActivitySupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(Fragment.class.getCanonicalName())
                .commit();
    }

}
