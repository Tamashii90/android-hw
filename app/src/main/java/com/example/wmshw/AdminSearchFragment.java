package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class AdminSearchFragment extends Fragment {
    SharedPreferences sharedPreferences;
    EditText locationField;
    EditText driverField;
    EditText plugedNumberField;
    EditText fromDateField;
    EditText toDateField;
    ProgressBar progressBar;

    public AdminSearchFragment() {
        super(R.layout.fragment_admin_search);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        locationField = view.findViewById(R.id.edit_text_search_location);
        driverField = view.findViewById(R.id.edit_text_search_driver);
        plugedNumberField = view.findViewById(R.id.edit_text_search_pluged_number);
        fromDateField = view.findViewById(R.id.edit_text_search_date_from);
        toDateField = view.findViewById(R.id.edit_text_search_date_to);
        progressBar = view.findViewById(R.id.progessBar_search);
        view.findViewById(R.id.button_search).setOnClickListener(this::search);
    }

    public void search(View view) {
        String plugedNumber = plugedNumberField.getText().toString();
        String driver = driverField.getText().toString();
        String location = locationField.getText().toString();
        String fromDate = fromDateField.getText().toString();
        String toDate = toDateField.getText().toString();
        String token = "Bearer " + sharedPreferences.getString("token", null);

        Call<JsonArray> request = MyApi.instance.getViolationLogs(
                token, plugedNumber, driver, location, fromDate, toDate
        );
        progressBar.setVisibility(View.VISIBLE);
        request.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray violationCardsJson = response.body();
                    NavDirections action = AdminSearchFragmentDirections.actionAdminSearchFragmentToViolationsFragment(violationCardsJson.toString());
                    Navigation.findNavController(view).navigate(action);

                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}