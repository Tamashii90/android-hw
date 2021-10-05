package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.wmshw.model.AddViolationRequest;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddViolationFragment extends Fragment {
    SharedPreferences sharedPreferences;
    String plugedNumber;
    TextView plugedNumberField;
    Spinner violationTypeField;
    EditText locationField;
    Button submitViolationBtn;
    ProgressBar progressBar;

    public AddViolationFragment() {
        super(R.layout.fragment_add_violation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        plugedNumber = AddViolationFragmentArgs.fromBundle(getArguments()).getPlugedNumber();

        plugedNumberField = view.findViewById(R.id.editText_add_violation_pluged_number);
        violationTypeField = view.findViewById(R.id.spinner_violation_type);
        locationField = view.findViewById(R.id.editText_add_violation_location);
        progressBar = view.findViewById(R.id.progressBar_add_violation);
        submitViolationBtn = view.findViewById(R.id.button_submit_add_violation);

        plugedNumberField.setText(plugedNumber);

        populateDropDownList();

        submitViolationBtn.setOnClickListener(this::submitViolation);
    }

    private void populateDropDownList() {
        Call<String[]> request = MyApi.instance.getViolationTypes();
        request.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    String[] types = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, types);
                    violationTypeField.setAdapter(adapter);
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

    public void submitViolation(View view) {
        String token = "Bearer " + sharedPreferences.getString("token", null);
        String location = locationField.getText().toString();
        String violationType = violationTypeField.getSelectedItem().toString();
        AddViolationRequest addViolationRequest = new AddViolationRequest(
                plugedNumber, violationType, location);

        Call<Void> request = MyApi.instance.addViolationLog(token, addViolationRequest);
        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Navigation.findNavController(view).navigateUp();
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
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
}