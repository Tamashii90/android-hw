package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;
import com.example.wmshw.retrofit.MyApi;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<ViolationCard> violationCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TODO display less fields
        TextView textPlugedNumber;
        TextView textLocation;
        TextView textPaid;
        TextView textDate;
        TextView textTax;
        TextView textDriver;
        RelativeLayout cardContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlugedNumber = itemView.findViewById(R.id.textView_plugedNumberValue);
            textLocation = itemView.findViewById(R.id.textView_locationValue);
            textPaid = itemView.findViewById(R.id.textView_paid);
            textDate = itemView.findViewById(R.id.textView_date);
            textTax = itemView.findViewById(R.id.textView_tax);
            textDriver = itemView.findViewById(R.id.textView_driverValue);
            cardContainer = itemView.findViewById(R.id.card_container);
        }
    }

    public MyAdapter(List<ViolationCard> violationCards) {
        this.violationCards = violationCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.violation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViolationCard card = violationCards.get(position);
        holder.textTax.setText(String.valueOf(card.getTax()));
        holder.textPlugedNumber.setText(card.getPlugedNumber());
        holder.textDate.setText(card.getDate());
        holder.textDriver.setText(card.getDriver());
        holder.textLocation.setText(card.getLocation());
        if (card.isPaid()) {
            holder.textPaid.setText("Paid");
            holder.textPaid.setTextColor(Color.parseColor("#03c457"));
        } else {
            holder.textPaid.setText("Not Paid");
            holder.textPaid.setTextColor(Color.parseColor("#fb2e0e"));
        }
        holder.cardContainer.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = "Bearer " + sharedPreferences.getString("token", null);
            long id = card.getId();
            Call<JsonObject> violationCardCall = MyApi.instance.getViolationLog(token, id);
            violationCardCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        NavDirections action = AdminViolationsFragmentDirections.actionViolationsFragmentToViolationDetailsFragment(
                                response.body().toString());
                        Navigation.findNavController(v).navigate(action);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(v.getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return violationCards.size();
    }

    public void setItems(List<ViolationCard> violationCards) {
        this.violationCards = violationCards;
        notifyDataSetChanged();
    }
}
