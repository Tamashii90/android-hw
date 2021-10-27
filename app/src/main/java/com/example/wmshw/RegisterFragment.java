package com.example.wmshw;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wmshw.model.JwtResponse;
import com.example.wmshw.model.RegisterRequest;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterFragment extends Fragment {
    EditText driverField;
    EditText plugedNumberField;
    EditText repeatPlugedNumberField;
    Spinner vehicleTypeField;
    EditText productionDateField;
    ProgressBar progressBar;
    String[] types;
    DatePickerDialog.OnDateSetListener datePickListener;
    Calendar myCalendar = Calendar.getInstance();

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        driverField = view.findViewById(R.id.edit_text_register_driver);
        plugedNumberField = view.findViewById(R.id.edit_text_register_plugedNumber);
        repeatPlugedNumberField = view.findViewById(R.id.edit_text_register_repeat_plugedNumber);
        vehicleTypeField = view.findViewById(R.id.spinner_register_type);
        productionDateField = view.findViewById(R.id.edit_text_register_prodDate);
        progressBar = view.findViewById(R.id.progressBar_register);

        datePickListener = (DatePicker datePicker, int year, int month, int day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            productionDateField.setText(sdf.format(myCalendar.getTime()));
        };

        view.findViewById(R.id.button_register).setOnClickListener(this::register);
        productionDateField.setOnClickListener(this::showDateDialog);
        if (savedInstanceState == null) {
            populateTypesList();
        } else {
            types = savedInstanceState.getStringArray("types");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, types);
            vehicleTypeField.setAdapter(adapter);
            vehicleTypeField.setSelection(savedInstanceState.getInt("selection"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("types", types);
        outState.putInt("selection", vehicleTypeField.getSelectedItemPosition());
    }

    private void showDateDialog(View view) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                R.style.datepicker,
                datePickListener,
                year, month, day);
        dialog.show();
    }

    private void populateTypesList() {
        Call<String[]> request = MyApi.instance.getVehicleTypes();
        request.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    types = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, types);
                    vehicleTypeField.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view) {
        MyUtils.hideKeyboard(view);

        String driver = driverField.getText().toString();
        String plugedNumber = plugedNumberField.getText().toString();
        String repeatPlugedNumber = repeatPlugedNumberField.getText().toString();
        String type = vehicleTypeField.getSelectedItem().toString();
        String productionDate = productionDateField.getText().toString();
        boolean crossOut = false;

        if (MyUtils.hasEmptyString(
                driver, plugedNumber, repeatPlugedNumber, type, productionDate
        )) {
            Toast.makeText(getActivity(), "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!plugedNumber.equals(repeatPlugedNumber)) {
            Toast.makeText(getContext(), "Pluged Numbers don't match.", Toast.LENGTH_LONG).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(
                driver, plugedNumber, repeatPlugedNumber, type, productionDate
        );

        Call<JwtResponse> call = MyApi.instance.postRegister(registerRequest);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful()) {
                    JwtResponse jwtResponse = response.body();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", jwtResponse.getJwt());
                    editor.putString("user", driver);
                    editor.putString("plugedNumber", plugedNumber);
                    editor.putString("authority", "USER");
                    editor.apply();
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getActivity(), UserActivity.class));
                } else {
                    Toast.makeText(getContext(), MyApi.getErrorMessage(response), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}