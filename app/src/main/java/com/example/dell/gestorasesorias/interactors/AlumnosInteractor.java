package com.example.dell.gestorasesorias.interactors;

import com.example.dell.gestorasesorias.callbacks.AlumnosCallback;
import com.example.dell.gestorasesorias.callbacks.RetrofitCallback;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;
import com.example.dell.gestorasesorias.data.models.error.Error;
import com.example.dell.gestorasesorias.services.AlumnosService;
import com.example.dell.gestorasesorias.services.ServiceGenerator;

/**
 * Created by Dell on 04/11/2018.
 */

public class AlumnosInteractor {

    private AlumnosService alumnosService;
    private AlumnosCallback alumnosCallback;

    public AlumnosInteractor(AlumnosCallback alumnosCallback) {
        this.alumnosService = ServiceGenerator.RETROFIT.create(AlumnosService.class);
        this.alumnosCallback = alumnosCallback;
    }

    public void callAlumnos(){
        this.alumnosService.get()
                .enqueue(new RetrofitCallback<AlumnosResponse>() {
                    @Override
                    public void onResponseSuccess(AlumnosResponse response) {
                        alumnosCallback.onAlumnosResponse(response);
                    }

                    @Override
                    public void onApiResponseError(Error error) {
                        alumnosCallback.onApiResponseError(error);
                    }
                });
    }
}
