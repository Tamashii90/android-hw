package com.example.wmshw.retrofit;

import com.example.wmshw.RegisterRequest;
import com.example.wmshw.model.ViolationCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface MyApiInterface {
    @POST("api/login")
    Call<JwtResponse> postLogin(@Body AuthRequest authRequest);

    @POST("api/register")
    Call<JwtResponse> postRegister(@Body RegisterRequest registerRequest);

    @GET("api/violations-log")
    Call<List<ViolationCard>> getViolationLogs(@Header("Authorization") String token);
}
