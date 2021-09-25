package com.example.wmshw;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<ViolationCard> violationCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPlugedNumber;
        TextView textLocation;
        TextView textPaid;
        TextView textDate;
        TextView textTax;
        TextView textDriver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlugedNumber = itemView.findViewById(R.id.textView_plugedNumberValue);
            textLocation = itemView.findViewById(R.id.textView_locationValue);
            textPaid = itemView.findViewById(R.id.textView_paid);
            textDate = itemView.findViewById(R.id.textView_date);
            textTax = itemView.findViewById(R.id.textView_tax);
            textDriver = itemView.findViewById(R.id.textView_driverValue);
        }
    }

    public MyAdapter(List<ViolationCard> violationCards) {
        this.violationCards = violationCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViolationCard card = violationCards.get(position);
        holder.textTax.setText(String.valueOf(card.getTax()));
        holder.textPlugedNumber.setText(card.getPlugedNumber());
        holder.textDate.setText(card.getDate().toString());
        holder.textDriver.setText(card.getDriver());
        holder.textLocation.setText(card.getLocation());
        if (card.isPaid()) {
            holder.textPaid.setText("Paid");
            holder.textPaid.setTextColor(Color.parseColor("#03c457"));
        } else {
            holder.textPaid.setText("Not Paid");
            holder.textPaid.setTextColor(Color.parseColor("#fb2e0e"));
        }
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
