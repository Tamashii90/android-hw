package com.example.wmshw.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class MyApi {
    public static final String BASE_URL = "https://e-traffic.herokuapp.com";
    public static final MyApiInterface instance = new Retrofit.Builder()
            .baseUrl(MyApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApiInterface.class);
    private static final Gson GSON = new Gson();

    public static String getErrorMessage(Response response) {
        String message = "Error";
        ResponseBody errBody = response.errorBody();
        if (errBody != null) {
            try {
                String resJson = errBody.string();
                JsonObject resObj = GSON.fromJson(resJson, JsonObject.class);
                message = resObj.get("message").getAsString();
            } catch (IOException e) {
            }
        }
        return message;
    }
}
