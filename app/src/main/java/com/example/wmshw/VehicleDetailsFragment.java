package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.wmshw.model.Vehicle;
import com.example.wmshw.retrofit.MyApi;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class VehicleDetailsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView plugedNumberField;
    TextView driverField;
    TextView typeField;
    TextView registrationDateField;
    TextView productionDateField;
    TextView crossOutField;
    Button navigateToAddViolBtn;
    Button crossOutBtn;
    Button uncrossOutBtn;
    ProgressBar progressBar;

    public VehicleDetailsFragment() {
        super(R.layout.fragment_vehicle_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        plugedNumberField = view.findViewById(R.id.textView_vehicle_pluged_number);
        driverField = view.findViewById(R.id.textView_vehicle_driver);
        typeField = view.findViewById(R.id.textView_vehicle_type);
        registrationDateField = view.findViewById(R.id.textView_register_date);
        productionDateField = view.findViewById(R.id.textView_prod_date);
        crossOutField = view.findViewById(R.id.textView_register_crossOut);
        crossOutBtn = view.findViewById(R.id.button_cross_out);
        uncrossOutBtn = view.findViewById(R.id.button_uncross_out);
        navigateToAddViolBtn = view.findViewById(R.id.button_navigate_to_add_violation);
        progressBar = view.findViewById(R.id.progressBar_vehicle);

        Gson gson = new Gson();
        String vehicleJson = VehicleDetailsFragmentArgs.fromBundle(getArguments()).getVehicleJson();
        Vehicle vehicle = gson.fromJson(vehicleJson, Vehicle.class);
        plugedNumberField.setText(vehicle.getPlugedNumber());
        driverField.setText(vehicle.getDriver());
        typeField.setText(vehicle.getType());
        productionDateField.setText(vehicle.getProductionDate());
        registrationDateField.setText(vehicle.getRegistrationDate());
        crossOutField.setText(vehicle.isCrossOut() ? "Yes" : "No");

        navigateToAddViolBtn.setOnClickListener(this::navigateToAddViolation);

        if (vehicle.isCrossOut()) {
            crossOutBtn.setVisibility(View.GONE);
            uncrossOutBtn.setOnClickListener(this::editCrossOut);
        } else {
            uncrossOutBtn.setVisibility(View.GONE);
            crossOutBtn.setOnClickListener(this::editCrossOut);
        }
    }

    public void editCrossOut(View view) {
        String token = "Bearer " + sharedPreferences.getString("token", null);
        String plugedNumber = plugedNumberField.getText().toString();
        Map<String, Boolean> requestBody = new HashMap<>();
        Boolean crossOut;
        crossOut = view.getId() == R.id.button_cross_out;
        requestBody.put("crossOut", crossOut);
        Call<Void> request = MyApi.instance.editCrossOut(token, plugedNumber, requestBody);

        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Navigation.findNavController(view).navigateUp();
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), MyApi.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToAddViolation(View view) {
        String plugedNumber = plugedNumberField.getText().toString();
        String driver = driverField.getText().toString();
        NavDirections action = VehicleDetailsFragmentDirections
                .actionVehicleDetailsFragmentToAddViolationFragment(plugedNumber);
        Navigation.findNavController(view).navigate(action);
    }
}