package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.retrofit.MyApi;
import com.example.wmshw.retrofit.ViolationLogResponse;
import org.threeten.bp.LocalDate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class AdminViolationsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    List<ViolationCard> violationCards = new ArrayList<>();

    public AdminViolationsFragment() {
        super(R.layout.fragment_admin_violations);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = "Bearer ";
        token += sharedPreferences.getString("token", null);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        MyAdapter adapter = new MyAdapter(violationCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        Call<List<ViolationLogResponse>> request = MyApi.instance.getViolationLogs(token);
        request.enqueue(new Callback<List<ViolationLogResponse>>() {
            @Override
            public void onResponse(Call<List<ViolationLogResponse>> call, Response<List<ViolationLogResponse>> response) {
                if (response.isSuccessful()) {
                    List<ViolationLogResponse> deserializedResponse = response.body();
                    for (ViolationLogResponse thisLog : deserializedResponse) {
                        ViolationCard newCard = new ViolationCard();
                        newCard.setDate(LocalDate.parse(thisLog.getDate()));
                        newCard.setDriver(thisLog.getVehicle().get("driver").getAsString());
                        newCard.setPlugedNumber(thisLog.getVehicle().get("plugedNumber").getAsString());
                        newCard.setTax(thisLog.getViolation().get("tax").getAsLong());
                        newCard.setLocation(thisLog.getLocation());
                        newCard.setPaid(thisLog.isPaid());
                        violationCards.add(newCard);
                    }
                    adapter.setItems(violationCards);
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViolationLogResponse>> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                Toast.makeText(getActivity(),"Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}