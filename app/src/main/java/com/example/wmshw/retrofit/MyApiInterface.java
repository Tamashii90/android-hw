package com.example.wmshw.retrofit;

import com.example.wmshw.RegisterRequest;
import com.example.wmshw.model.ViolationCard;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MyApiInterface {
    @POST("api/login")
    Call<JwtResponse> postLogin(@Body AuthRequest authRequest);

    @POST("api/register")
    Call<JwtResponse> postRegister(@Body RegisterRequest registerRequest);

    @GET("api/violations-log")
    Call<List<ViolationCard>> getViolationLogs(@Header("Authorization") String token,
                                               @Query("plugedNumber") String plugedNumber,
                                               @Query("driver") String driver,
                                               @Query("location") String location,
                                               @Query("fromDate") String fromDate,
                                               @Query("toDate") String toDate
    );

    @GET("api/violations-log/{id}")
    Call<ViolationCard> getViolationLog(@Header("Authorization") String token,
                                        @Path("id") long id);
}
