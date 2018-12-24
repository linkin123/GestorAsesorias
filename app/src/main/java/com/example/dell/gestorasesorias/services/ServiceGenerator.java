package com.example.dell.gestorasesorias.services;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 03/11/2018.
 */

public class ServiceGenerator {
    public static final String BASE_URL = "http://demo4643202.mockable.io/";

    /*instancia el interceptor manda mensajes al logcat de los servicios*/
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    /*construye el cliente http*/
    private static OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
            /*tiempo de lectura*/
            .readTimeout(60, TimeUnit.SECONDS)
            /*tiempo de conexion*/
            .connectTimeout(60, TimeUnit.SECONDS)
            /*colocamos el nivel de log e insertamos el interceptor : body imprime todoo*/
            .addInterceptor(httpLoggingInterceptor);

    private static final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            /*si el campo no tiene esa anotacion serializable la ignora*/
            for (Annotation annotation : f.getAnnotations()) {
                if (annotation instanceof SerializedName) {
                    return false;
                }
            }
            return true;
        }
        /*ignora clases*/
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).create();

    /*creamos nuestra instancia de retrofit con la url y agregamos el metodo de conversion*/
    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    /*construimos la instancia de la clase retrofit con el cliente*/
    public static final Retrofit RETROFIT = retrofitBuilder.client(httpBuilder.build())
            .build();
}
