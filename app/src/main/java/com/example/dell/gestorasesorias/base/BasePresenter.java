package com.example.dell.gestorasesorias.base;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by Dell on 01/06/2018.
 */

public class BasePresenter <V extends Presenter.View> implements Presenter<V>{

    private WeakReference<V> view;

    @Override
    public void setView(@NonNull V view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public V getView() {
        return view == null ? null : view.get();
    }

    @Override
    public boolean isViewAttached() {
        return view != null && view.get() != null;
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        this.view.clear();
    }

    @Override
    public void terminate() {
        view = null;
    }
}
