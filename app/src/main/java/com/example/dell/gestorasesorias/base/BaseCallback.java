package com.example.dell.gestorasesorias.base;


import com.example.dell.gestorasesorias.data.models.error.Error;

/**
 * Created by Dell on 04/11/2018.
 */

public interface BaseCallback {
    void onApiResponseError(Error error);
}
