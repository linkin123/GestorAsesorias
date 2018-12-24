package com.example.dell.gestorasesorias.base;

import android.support.annotation.NonNull;

/**
 * Created by Dell on 01/06/2018.
 */

public interface Presenter <V>{

    void setView(@NonNull V view);

    V getView();

    boolean isViewAttached();

    void start();

    void resume();

    void pause();

    void stop();

    void terminate();

    interface View {


    }
}
