package com.example.wmshw;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyApi {
    String BASE_URL = "https://tamashii-spring.herokuapp.com/";
    @POST("api/login")
    Call<JwtResponse> postLogin(@Body AuthRequest authRequest);


    @POST("api/register")
    Call<JwtResponse> postRegister(@Body RegisterRequest registerRequest);

}
