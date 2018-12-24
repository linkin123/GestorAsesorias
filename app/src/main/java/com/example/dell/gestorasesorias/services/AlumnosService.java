package com.example.dell.gestorasesorias.services;

import com.example.dell.gestorasesorias.data.models.AlumnosResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dell on 03/11/2018.
 */

public interface AlumnosService {
    @GET("/alumnos")
    Call<AlumnosResponse> get();
}
