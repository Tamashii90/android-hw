package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class AdminViolationsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    MyAdapter adapter;
    List<ViolationCard> violationCards = new ArrayList<>();
    EditText locationField;
    EditText driverField;
    EditText plugedNumberField;
    EditText fromDateField;
    EditText toDateField;


    public AdminViolationsFragment() {
        super(R.layout.fragment_admin_violations);
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

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        adapter = new MyAdapter(violationCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.button_search).setOnClickListener(this::search);
    }

    public void search(View view) {
        String token = "Bearer " + sharedPreferences.getString("token", null);
        String location = TextUtils.isEmpty(locationField.getText()) ? null : locationField.getText().toString();
        String driver = TextUtils.isEmpty(driverField.getText()) ? null : driverField.getText().toString();
        String plugedNumber = TextUtils.isEmpty(plugedNumberField.getText()) ? null : plugedNumberField.getText().toString();
        String fromDate = TextUtils.isEmpty(fromDateField.getText()) ? null : fromDateField.getText().toString();
        String toDate = TextUtils.isEmpty(toDateField.getText()) ? null : toDateField.getText().toString();

        Call<List<ViolationCard>> request = MyApi.instance.getViolationLogs(
                token, plugedNumber, driver, location, fromDate, toDate
        );
        request.enqueue(new Callback<List<ViolationCard>>() {
            @Override
            public void onResponse(Call<List<ViolationCard>> call, Response<List<ViolationCard>> response) {
                if (response.isSuccessful()) {
                    List<ViolationCard> violationCards = response.body();
                    adapter.setItems(violationCards);
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViolationCard>> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}