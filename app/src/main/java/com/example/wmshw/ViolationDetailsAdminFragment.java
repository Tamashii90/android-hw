package com.example.wmshw;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.model.ViolationLogEditRequest;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViolationDetailsAdminFragment extends Fragment {

    SharedPreferences sharedPreferences;
    ViolationCard card;
    String[] types;
    EditText locationField;
    TextView driverField;
    TextView plugedNumberField;
    EditText dateField;
    TextView taxField;
    Spinner typeField;
    Switch paidField;
    Button updateBtn;
    ProgressBar progressBar;
    FrameLayout progressOverlay;
    DatePickerDialog.OnDateSetListener datePickListener;
    Calendar myCalendar = Calendar.getInstance();


    public ViolationDetailsAdminFragment() {
        super(R.layout.fragment_violation_details_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        progressOverlay = view.findViewById(R.id.progressBarHolderDetails);
        progressBar = view.findViewById(R.id.progressBar_update);
        locationField = view.findViewById(R.id.edit_text_location);
        driverField = view.findViewById(R.id.textView_driver);
        plugedNumberField = view.findViewById(R.id.textView_pluged_number);
        dateField = view.findViewById(R.id.edit_text_date);
        taxField = view.findViewById(R.id.textView_tax_admin);
        typeField = view.findViewById(R.id.spinner_violation_type);
        paidField = view.findViewById(R.id.switch_paid);
        updateBtn = view.findViewById(R.id.button_update_violation);
        updateBtn.setOnClickListener(this::updateViolation);
        dateField.setOnClickListener(this::showDateDialog);

        datePickListener = (DatePicker datePicker, int year, int month, int day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateField.setText(sdf.format(myCalendar.getTime()));
        };
        if (savedInstanceState == null) {
            fetchViolationDetails();
        } else {
            types = savedInstanceState.getStringArray("types");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, types);
            typeField.setAdapter(adapter);
            typeField.setSelection(savedInstanceState.getInt("selection", 0));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("types", types);
        outState.putInt("selection", typeField.getSelectedItemPosition());
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

    private void fetchViolationDetails() {
        Long id = ViolationDetailsAdminFragmentArgs.fromBundle(getArguments()).getId();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<ViolationCard> violationCardCall = MyApi.instance.getViolationLog(token, id);

        progressOverlay.setVisibility(View.VISIBLE);
        violationCardCall.enqueue(new Callback<ViolationCard>() {
            @Override
            public void onResponse(Call<ViolationCard> call, Response<ViolationCard> response) {
                if (response.isSuccessful()) {
                    card = response.body();
                    paidField.setChecked(card.isPaid());
                    plugedNumberField.setText(card.getPlugedNumber());
                    driverField.setText(card.getDriver());
                    locationField.setText(card.getLocation());
                    taxField.setText("$" + String.valueOf(card.getTax()));
                    dateField.setText(card.getDate());
                    progressOverlay.setVisibility(View.GONE);
                    populateTypeField();
                }
            }

            @Override
            public void onFailure(Call<ViolationCard> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                progressOverlay.setVisibility(View.GONE);
            }
        });
    }

    private void populateTypeField() {
        Call<String[]> request = MyApi.instance.getViolationTypes();
        request.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // it will be null if the user touched the back button
                // before the response is received
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        types = response.body();
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, types);
                        typeField.setAdapter(adapter);
                        typeField.setSelection(Arrays.asList(types).indexOf(card.getType()));
                    } else {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateViolation(View view) {
        String authHeader = "Bearer " + sharedPreferences.getString("token", null);
        String type = typeField.getSelectedItem().toString();
        String location = locationField.getText().toString();
        String date = dateField.getText().toString();
        boolean paid = paidField.isChecked();

        if (MyUtils.hasEmptyString(type, location, date)) {
            Toast.makeText(getActivity(), "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        ViolationLogEditRequest editRequest = new ViolationLogEditRequest(
                type, location, date, paid
        );
        Call<Void> request = MyApi.instance.updateViolationLog(authHeader, card.getId(), editRequest);

        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    refreshViolationsList(view);
                } else {
                    Toast.makeText(getActivity(), MyApi.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void refreshViolationsList(View view) {

        String plugedNumber = ViolationsListData.SearchCriteria.getPlugedNumber();
        String driver = ViolationsListData.SearchCriteria.getDriver();
        String location = ViolationsListData.SearchCriteria.getLocation();
        String fromDate = ViolationsListData.SearchCriteria.getFromDate();
        String toDate = ViolationsListData.SearchCriteria.getToDate();
        String token = "Bearer " + sharedPreferences.getString("token", null);

        Call<List<ViolationCard>> request = MyApi.instance.getViolationLogs(
                token, plugedNumber, driver, location, fromDate, toDate
        );
        progressOverlay.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<List<ViolationCard>>() {
            @Override
            public void onResponse(Call<List<ViolationCard>> call, Response<List<ViolationCard>> response) {
                if (response.isSuccessful()) {
                    List<ViolationCard> violationCards = response.body();
                    ViolationsListData.setList(violationCards);
                    Navigation.findNavController(view).navigateUp();
                } else {
                    progressOverlay.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViolationCard>> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                progressOverlay.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}