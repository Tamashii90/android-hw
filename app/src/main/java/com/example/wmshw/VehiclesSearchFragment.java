package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.wmshw.retrofit.MyApi;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiclesSearchFragment extends Fragment {
    SharedPreferences sharedPreferences;
    EditText plateNumberField;
    TextView registerLink;
    Button submitSearchBtn;
    ProgressBar progressBar;


    public VehiclesSearchFragment() {
        super(R.layout.fragment_vehicles);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        plateNumberField = view.findViewById(R.id.editText_vehicles_plate_number);
        submitSearchBtn = view.findViewById(R.id.button_vehicles_search);
        registerLink = view.findViewById(R.id.textView_register_vehicle);
        progressBar = view.findViewById(R.id.progessBar_vehicles_search);

        submitSearchBtn.setOnClickListener(this::searchForVehicle);
        registerLink.setOnClickListener(this::navigateToRegister);
    }

    void navigateToRegister(View view) {
        NavDirections action = VehiclesSearchFragmentDirections.actionVehiclesFragmentToRegisterFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void searchForVehicle(View view) {
        MyUtils.hideKeyboard(view);

        String plateNumber = plateNumberField.getText().toString();
        String token = "Bearer " + sharedPreferences.getString("token", null);

        if (plateNumber.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Plate Number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<JsonObject> request = MyApi.instance.getVehicle(token, plateNumber);
        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject vehicleJson = response.body();
                    NavDirections action = VehiclesSearchFragmentDirections
                            .actionVehiclesFragmentToVehicleDetailsFragment(vehicleJson.toString());
                    Navigation.findNavController(view).navigate(action);
                } else {
                    Toast.makeText(getActivity(), MyApi.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}