package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViolationDetailsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    String authority;
    View locationField;
    View driverField;
    View plugedNumberField;
    View dateField;
    View taxField;
    View typeField;
    View paidField;
    Button updateBtn;
    Button payBtn;
    FrameLayout progressOverlay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        authority = sharedPreferences.getString("authority", null);
        View view;
        if (authority.equals("USER")) {
            view = inflater.inflate(R.layout.fragment_violation_details_user, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_violation_details_admin, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressOverlay = view.findViewById(R.id.progressBarHolderDetails);
        if (authority.equals("ADMIN")) {
            locationField = view.findViewById(R.id.edit_text_location);
            driverField = view.findViewById(R.id.edit_text_driver);
            plugedNumberField = view.findViewById(R.id.edit_text_pluged_number);
            dateField = view.findViewById(R.id.edit_text_date);
            taxField = view.findViewById(R.id.edit_text_tax);
            typeField = view.findViewById(R.id.edit_text_type);
            paidField = view.findViewById(R.id.edit_text_paid);
            updateBtn = view.findViewById(R.id.button_update_violation);
            updateBtn.setOnClickListener(this::updateViolation);
        } else {
            locationField = view.findViewById(R.id.textView_user_location);
            driverField = view.findViewById(R.id.textView_user_driver);
            plugedNumberField = view.findViewById(R.id.textView_user_pluged_number);
            dateField = view.findViewById(R.id.textView_user_date);
            taxField = view.findViewById(R.id.textView_user_tax);
            typeField = view.findViewById(R.id.textView_user_type);
            payBtn = view.findViewById(R.id.button_pay);
            payBtn.setOnClickListener(this::payForViolation);
        }
        fetchViolationDetails();
    }

    private void fetchViolationDetails() {
        long id = ViolationDetailsFragmentArgs.fromBundle(getArguments()).getId();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<ViolationCard> violationCardCall = MyApi.instance.getViolationLog(token, id);

        progressOverlay.setVisibility(View.VISIBLE);
        violationCardCall.enqueue(new Callback<ViolationCard>() {
            @Override
            public void onResponse(Call<ViolationCard> call, Response<ViolationCard> response) {
                if (response.isSuccessful()) {
                    ViolationCard card = response.body();
                    if (authority.equals("ADMIN")) {
                        ((EditText) paidField).setText(String.valueOf(card.isPaid()));
                        ((EditText) plugedNumberField).setText(card.getPlugedNumber());
                        ((EditText) driverField).setText(card.getDriver());
                        ((EditText) locationField).setText(card.getLocation());
                        ((EditText) taxField).setText(String.valueOf(card.getTax()));
                        ((EditText) dateField).setText(card.getDate());
                        ((EditText) typeField).setText(card.getType());
                    } else {
                        ((TextView) plugedNumberField).setText(card.getPlugedNumber());
                        ((TextView) driverField).setText(card.getDriver());
                        ((TextView) locationField).setText(card.getLocation());
                        ((TextView) taxField).setText(String.valueOf(card.getTax()));
                        ((TextView) dateField).setText(card.getDate());
                        ((TextView) typeField).setText(card.getType());
                    }
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

    // TODO implement this
    public void payForViolation(View view) {
        Toast.makeText(getActivity(), "Pay!", Toast.LENGTH_SHORT).show();
    }
}