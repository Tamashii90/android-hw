package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViolationDetailsAdminFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText locationField;
    EditText driverField;
    EditText plugedNumberField;
    EditText dateField;
    EditText taxField;
    EditText typeField;
    EditText paidField;
    Button updateBtn;
    FrameLayout progressOverlay;

    public ViolationDetailsAdminFragment() {
        super(R.layout.fragment_violation_details_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        progressOverlay = view.findViewById(R.id.progressBarHolderDetails);
        locationField = view.findViewById(R.id.edit_text_location);
        driverField = view.findViewById(R.id.edit_text_driver);
        plugedNumberField = view.findViewById(R.id.edit_text_pluged_number);
        dateField = view.findViewById(R.id.edit_text_date);
        taxField = view.findViewById(R.id.edit_text_tax);
        typeField = view.findViewById(R.id.edit_text_type);
        paidField = view.findViewById(R.id.edit_text_paid);
        updateBtn = view.findViewById(R.id.button_update_violation);
        updateBtn.setOnClickListener(this::updateViolation);

        fetchViolationDetails();
    }

    private void fetchViolationDetails() {
        Long id = ViolationDetailsAdminFragmentArgs.fromBundle(getArguments()).getId();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<ViolationCard> violationCardCall = MyApi.instance.getViolationLog(token, id);

        progressOverlay.setVisibility(View.VISIBLE);
        violationCardCall.enqueue(new Callback<ViolationCard>() {
            @Override
            public void onResponse(Call<ViolationCard> call, Response<ViolationCard> response) {
                if (response.isSuccessful()) {
                    ViolationCard card = response.body();
                    paidField.setText(String.valueOf(card.isPaid()));
                    plugedNumberField.setText(card.getPlugedNumber());
                    driverField.setText(card.getDriver());
                    locationField.setText(card.getLocation());
                    taxField.setText(String.valueOf(card.getTax()));
                    dateField.setText(card.getDate());
                    typeField.setText(card.getType());
                    progressOverlay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ViolationCard> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                progressOverlay.setVisibility(View.GONE);
            }
        });
    }

    // TODO implement this
    public void updateViolation(View view) {
        Toast.makeText(getActivity(), "Update!", Toast.LENGTH_SHORT).show();
    }
}