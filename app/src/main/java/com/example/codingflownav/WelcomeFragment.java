package com.example.codingflownav;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        super(R.layout.fragment_welcome);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String username = WelcomeFragmentArgs.fromBundle(getArguments()).getUsername();
        NavController navController = Navigation.findNavController(view);
        ((TextView) view.findViewById(R.id.text_view_username)).setText(username);

        view.findViewById(R.id.button_ok).setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), WorkflowActivity.class));
        });
    }
}