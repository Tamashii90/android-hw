package com.example.wmshw;

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
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ViolationDetailsUserFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView locationField;
    TextView driverField;
    TextView plugedNumberField;
    TextView dateField;
    TextView taxField;
    TextView typeField;
    Button payBtn;
    ProgressBar progressBar;
    FrameLayout progressOverlay;

    public ViolationDetailsUserFragment() {
        super(R.layout.fragment_violation_details_user);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        locationField = view.findViewById(R.id.textView_user_location);
        driverField = view.findViewById(R.id.textView_user_driver);
        plugedNumberField = view.findViewById(R.id.textView_user_pluged_number);
        dateField = view.findViewById(R.id.textView_user_date);
        taxField = view.findViewById(R.id.textView_user_tax);
        typeField = view.findViewById(R.id.textView_user_type);
        progressBar = view.findViewById(R.id.progressBar_pay);
        progressOverlay = view.findViewById(R.id.progressBarHolderDetails);
        payBtn = view.findViewById(R.id.button_pay);
        payBtn.setOnClickListener(this::payForViolation);

        if (savedInstanceState == null) {
            fetchViolationDetails();
        }
    }

    private void fetchViolationDetails() {
        Long id = ViolationDetailsUserFragmentArgs.fromBundle(getArguments()).getId();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<ViolationCard> violationCardCall = MyApi.instance.getViolationLog(token, id);

        progressOverlay.setVisibility(View.VISIBLE);
        violationCardCall.enqueue(new Callback<ViolationCard>() {
            @Override
            public void onResponse(Call<ViolationCard> call, Response<ViolationCard> response) {
                if (response.isSuccessful()) {
                    ViolationCard card = response.body();
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

    public void payForViolation(View view) {
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Long id = ViolationDetailsUserFragmentArgs.fromBundle(getArguments()).getId();
        Call<Void> request = MyApi.instance.payForViolation(token, id);

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

        String location = ViolationsListData.SearchCriteria.getLocation();
        String fromDate = ViolationsListData.SearchCriteria.getFromDate();
        String toDate = ViolationsListData.SearchCriteria.getToDate();
        String plugedNumber = sharedPreferences.getString("plugedNumber", null);
        String token = "Bearer " + sharedPreferences.getString("token", null);

        Call<List<ViolationCard>> request = MyApi.instance.getUsersViolationLogs(
                token, plugedNumber, location, fromDate, toDate
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
