package com.example.dell.gestorasesorias.callbacks;

import com.example.dell.gestorasesorias.base.BaseCallback;
import com.example.dell.gestorasesorias.data.models.error.Error;
import com.example.dell.gestorasesorias.services.ServiceGenerator;
import java.lang.annotation.Annotation;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


/**
 * Created by Dell on 04/11/2018.
 */

public abstract class RetrofitCallback<T> implements Callback<T>, BaseCallback {
    public abstract void onResponseSuccess(T response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                this.onResponseSuccess(response.body());
            } else {
                this.onApiResponseError(new Error("Error", "The request body is null"));
            }
        } else {
            this.handleError(response, this);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        this.onApiResponseError(new Error("Error", t.getLocalizedMessage()));
    }

    //maneja el error convirtiendolo a la clase personalizada.
    private <R> void handleError(Response<T> response, RetrofitCallback<T> baseCallBack) {
        Converter<ResponseBody, Error> converter = ServiceGenerator.RETROFIT.responseBodyConverter(Error.class, new Annotation[0]);
        try {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                baseCallBack.onApiResponseError(converter.convert(errorBody));
            } else {
                baseCallBack.onApiResponseError(new Error("Error", "Error body is null"));
            }
        } catch (IOException e) {
            baseCallBack.onApiResponseError(new Error("Error", e.getLocalizedMessage()));
        }
    }
}
