package com.example.wmshw;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class AdminSearchFragment extends Fragment {
    EditText locationField;
    EditText driverField;
    EditText plugedNumberField;
    EditText fromDateField;
    EditText toDateField;

    public AdminSearchFragment() {
        super(R.layout.fragment_admin_search);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationField = view.findViewById(R.id.edit_text_search_location);
        driverField = view.findViewById(R.id.edit_text_search_driver);
        plugedNumberField = view.findViewById(R.id.edit_text_search_pluged_number);
        fromDateField = view.findViewById(R.id.edit_text_search_date_from);
        toDateField = view.findViewById(R.id.edit_text_search_date_to);
        view.findViewById(R.id.button_search).setOnClickListener(this::triggerSearch);
    }

    public void triggerSearch(View view) {
        String plugedNumber = plugedNumberField.getText().toString();
        String driver = driverField.getText().toString();
        String location = locationField.getText().toString();
        String fromDate = fromDateField.getText().toString();
        String toDate = toDateField.getText().toString();
        NavDirections action = AdminSearchFragmentDirections.actionAdminSearchFragmentToViolationsFragment(
                plugedNumber, driver, location, fromDate, toDate);
        Navigation.findNavController(view).navigate(action);
    }
}