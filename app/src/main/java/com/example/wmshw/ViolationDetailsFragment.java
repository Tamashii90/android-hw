package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class ViolationDetailsFragment extends Fragment {

    SharedPreferences sharedPreferences;

    public ViolationDetailsFragment() {
        super(R.layout.fragment_violation_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        FrameLayout progressOverlay = view.findViewById(R.id.progressBarHolderDetails);
        long id = ViolationDetailsFragmentArgs.fromBundle(getArguments()).getId();
        String token = "Bearer " + sharedPreferences.getString("token", null);
        Call<ViolationCard> violationCardCall = MyApi.instance.getViolationLog(token, id);

        progressOverlay.setVisibility(View.VISIBLE);
        violationCardCall.enqueue(new Callback<ViolationCard>() {
            @Override
            public void onResponse(Call<ViolationCard> call, Response<ViolationCard> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
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
}