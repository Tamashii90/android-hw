package com.example.wmshw.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApi {
    public static final String BASE_URL = "https://tamashii-spring.herokuapp.com/";
    public static final MyApiInterface instance = new Retrofit.Builder()
            .baseUrl(MyApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApiInterface.class);
}
