package com.nodocivico.app.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // Cambia esta URL por la del servidor Flask del equipo
    private static final String BASE_URL = "http://10.0.2.2:5000/";

    private static volatile ApiService INSTANCE;

    public static ApiService getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiClient.class) {
                if (INSTANCE == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    INSTANCE = retrofit.create(ApiService.class);
                }
            }
        }
        return INSTANCE;
    }

    private ApiClient() {}
}
