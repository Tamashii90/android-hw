package com.example.wmshw.retrofit;

import com.example.wmshw.model.*;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface MyApiInterface {
    @POST("api/login")
    Call<JwtResponse> postLogin(@Body AuthRequest authRequest);

    @POST("api/register")
    Call<JwtResponse> postRegister(@Header("Authorization") String authHeader,
                                   @Body RegisterRequest registerRequest);

    @GET("api/violations-log")
    Call<List<ViolationCard>> getViolationLogs(@Header("Authorization") String token,
                                               @Query("plateNumber") String plateNumber,
                                               @Query("driver") String driver,
                                               @Query("location") String location,
                                               @Query("fromDate") String fromDate,
                                               @Query("toDate") String toDate
    );

    @GET("api/violations-log/user/{plateNumber}")
    Call<List<ViolationCard>> getUsersViolationLogs(
            @Header("Authorization") String token,
            @Path("plateNumber") String plateNumber,
            @Query("location") String location,
            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate
    );

    @GET("api/violations-log/{id}")
    Call<ViolationCard> getViolationLog(@Header("Authorization") String token,
                                        @Path("id") long id);

    @POST("api/violations-log")
    Call<Void> addViolationLog(@Header("Authorization") String token,
                               @Body AddViolationRequest addViolationRequest
    );

    @GET("api/vehicles/{plateNumber}")
    Call<JsonObject> getVehicle(@Header("Authorization") String token,
                                @Path("plateNumber") String plateNumber
    );

    @POST("api/vehicles/{plateNumber}")
    Call<Void> editCrossOut(@Header("Authorization") String token,
                            @Path("plateNumber") String plateNumber,
                            @Body Map<String, Boolean> map
    );

    @GET("api/violations")
    Call<String[]> getViolationTypes();


    @POST("api/violations-log/pay/{id}")
    Call<Void> payForViolation(
            @Header("Authorization") String token,
            @Path("id") Long id
    );

    @PUT("api/violations-log/{id}")
    Call<Void> updateViolationLog(@Header("Authorization") String authHeader,
                                  @Path("id") Long id,
                                  @Body ViolationLogEditRequest editRequest
    );

    @GET("/api/vehicles/types")
    Call<String[]> getVehicleTypes();
}
