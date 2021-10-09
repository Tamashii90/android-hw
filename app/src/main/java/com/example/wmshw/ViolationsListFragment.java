package com.example.wmshw;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wmshw.model.ViolationCard;

import java.util.ArrayList;
import java.util.List;

public class ViolationsListFragment extends Fragment {

    MyAdapter adapter;
    List<ViolationCard> violationCards = new ArrayList<>();
    TextView resultsCountField;
    TextView totalTaxField;


    public ViolationsListFragment() {
        super(R.layout.fragment_violations_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resultsCountField = view.findViewById(R.id.text_view_results_count);
        totalTaxField = view.findViewById(R.id.text_view_total_tax);

        violationCards = ViolationsListData.getList();
        String totalTax = getString(R.string.total_tax, calcTotalTax(violationCards));
        String resultsCount = getString(R.string.results_count, violationCards.size());
        resultsCountField.setText(resultsCount);
        if (violationCards.isEmpty()) {
            totalTaxField.setVisibility(View.INVISIBLE);
        } else {
            totalTaxField.setText(totalTax);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        adapter = new MyAdapter(violationCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private long calcTotalTax(List<ViolationCard> results) {
        long sum = 0;
        for (ViolationCard result : results) {
            sum += result.getTax();
        }
        return sum;
    }
}