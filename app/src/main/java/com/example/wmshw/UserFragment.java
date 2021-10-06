package com.example.wmshw;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.wmshw.retrofit.MyApi;
import com.google.gson.JsonArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserFragment extends Fragment {
    SharedPreferences sharedPreferences;
    EditText locationField;
    EditText fromDateField;
    EditText toDateField;
    EditText currentDateField;
    ProgressBar progressBar;
    DatePickerDialog.OnDateSetListener datePickListener;
    Calendar myCalendar = Calendar.getInstance();

    public UserFragment() {
        super(R.layout.fragment_user);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        locationField = view.findViewById(R.id.user_search_location);
        fromDateField = view.findViewById(R.id.user_search_date_from);
        toDateField = view.findViewById(R.id.user_search_date_to);
        progressBar = view.findViewById(R.id.user_progessBar_search);
        view.findViewById(R.id.user_button_search).setOnClickListener(this::search);
        fromDateField.setOnClickListener(this::showDateDialog);
        toDateField.setOnClickListener(this::showDateDialog);

        datePickListener = (DatePicker datePicker, int year, int month, int day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            currentDateField.setText(sdf.format(myCalendar.getTime()));
        };
    }

    private void showDateDialog(View view) {
        currentDateField = (EditText) view;
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

    public void search(View view) {
        String location = locationField.getText().toString();
        String fromDate = fromDateField.getText().toString();
        String toDate = toDateField.getText().toString();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        String plugedNumber = sharedPreferences.getString("plugedNumber", null);

        Call<JsonArray> request = MyApi.instance.getUsersViolationLogs(
                token, plugedNumber, location, fromDate, toDate
        );
        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray violationCardsJson = response.body();
                    NavDirections action = UserFragmentDirections.actionUserFragmentToUserViolationsFragment(
                            violationCardsJson.toString());
                    Navigation.findNavController(view).navigate(action);

                } else {
                    // TODO response.toString() is bad in production!
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}