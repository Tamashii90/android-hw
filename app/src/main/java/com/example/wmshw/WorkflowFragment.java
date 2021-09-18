package com.example.wmshw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WorkflowFragment extends Fragment {

    SharedPreferences sharedPreferences;

    public WorkflowFragment() {
        super(R.layout.fragment_workflow);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", null);
        Toast.makeText(getActivity(), String.format("Welcome, %s!", user), Toast.LENGTH_SHORT).show();
    }
}