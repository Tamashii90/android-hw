package com.example.codingflownav;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {
    String BASE_URL = "https://tamashii-spring.herokuapp.com/";
    @POST("api/login")
    Call<JwtResponse> postLogin(@Body AuthRequest authRequest);

}
