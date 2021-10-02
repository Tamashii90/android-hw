package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ViolationDetailsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText locationField;
    EditText driverField;
    EditText plugedNumberField;
    EditText dateField;
    EditText taxField;
    EditText typeField;
    EditText paidField;

    public ViolationDetailsFragment() {
        super(R.layout.fragment_violation_details);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        locationField = view.findViewById(R.id.edit_text_location);
        driverField = view.findViewById(R.id.edit_text_driver);
        plugedNumberField = view.findViewById(R.id.edit_text_pluged_number);
        dateField = view.findViewById(R.id.edit_text_date);
        taxField = view.findViewById(R.id.edit_text_tax);
        typeField = view.findViewById(R.id.edit_text_type);
        paidField = view.findViewById(R.id.edit_text_paid);

        Gson gson = new Gson();
        JsonObject violationJson = gson.fromJson(ViolationDetailsFragmentArgs.fromBundle(getArguments()).getViolationJson(), JsonObject.class);
        plugedNumberField.setText(violationJson.get("plugedNumber").getAsString());
        driverField.setText(violationJson.get("driver").getAsString());
        locationField.setText(violationJson.get("location").getAsString());
        taxField.setText(violationJson.get("tax").getAsString());
        paidField.setText(violationJson.get("paid").getAsString());
        dateField.setText(violationJson.get("date").getAsString());
        typeField.setText(violationJson.get("type").getAsString());

    }
}