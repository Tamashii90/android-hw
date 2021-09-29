package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
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
    TextView resultsCountField;
    TextView totalTaxField;
    FrameLayout progressOverlay;


    public AdminViolationsFragment() {
        super(R.layout.fragment_admin_violations);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        resultsCountField = view.findViewById(R.id.text_view_results_count);
        totalTaxField = view.findViewById(R.id.text_view_total_tax);
        progressOverlay = view.findViewById(R.id.progressBarHolder);

        String plugedNumber = AdminViolationsFragmentArgs.fromBundle(getArguments()).getPlugedNumber();
        String driver = AdminViolationsFragmentArgs.fromBundle(getArguments()).getDriver();
        String location = AdminViolationsFragmentArgs.fromBundle(getArguments()).getLocation();
        String fromDate = AdminViolationsFragmentArgs.fromBundle(getArguments()).getFromDate();
        String toDate = AdminViolationsFragmentArgs.fromBundle(getArguments()).getToDate();

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        adapter = new MyAdapter(violationCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        progressOverlay.setVisibility(View.VISIBLE);
        search(plugedNumber, driver, location, fromDate, toDate);
    }

    public void search(String plugedNumber, String driver, String location, String fromDate, String toDate) {
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<List<ViolationCard>> request = MyApi.instance.getViolationLogs(
                token, plugedNumber, driver, location, fromDate, toDate
        );
        request.enqueue(new Callback<List<ViolationCard>>() {
            @Override
            public void onResponse(Call<List<ViolationCard>> call, Response<List<ViolationCard>> response) {
                if (response.isSuccessful()) {
                    List<ViolationCard> violationCards = response.body();
                    String totalTax = getString(R.string.total_tax, calcTotalTax(violationCards));
                    String resultsCount = getString(R.string.results_count, violationCards.size());
                    adapter.setItems(violationCards);
                    resultsCountField.setText(resultsCount);
                    if (violationCards.isEmpty()) {
                        totalTaxField.setVisibility(View.INVISIBLE);
                    } else {
                        totalTaxField.setText(totalTax);
                    }
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
                progressOverlay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ViolationCard>> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                totalTaxField.setVisibility(View.INVISIBLE);
                progressOverlay.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private long calcTotalTax(List<ViolationCard> results) {
        long sum = 0;
        for (ViolationCard result : results) {
            sum += result.getTax();
        }
        return sum;
    }
}