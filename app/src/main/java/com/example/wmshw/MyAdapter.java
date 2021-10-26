package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<ViolationCard> violationCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textLocation;
        TextView textDate;
        TextView textTax;
        TextView textDriver;
        ConstraintLayout cardContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textLocation = itemView.findViewById(R.id.textView_locationValue);
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
        holder.textTax.setText("$" + String.valueOf(card.getTax()));
        holder.textDate.setText(card.getDate());
        holder.textDriver.setText(card.getDriver());
        holder.textLocation.setText(card.getLocation());
        holder.cardContainer.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String authority = sharedPreferences.getString("authority", null);
            NavDirections action = authority.equals("USER") ?
                    ViolationsListFragmentDirections.actionViolationsListFragmentToViolationDetailsUserFragment(
                            card.getId()) : ViolationsListFragmentDirections.actionViolationsListFragmentToViolationDetailsAdminFragment(card.getId());
            Navigation.findNavController(v).navigate(action);
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
