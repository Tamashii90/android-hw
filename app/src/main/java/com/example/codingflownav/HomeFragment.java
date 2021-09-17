package com.example.codingflownav;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        view.findViewById(R.id.button_profile).setOnClickListener(view1 -> {
            String user = sharedPref.getString("user", null);
            NavController navController = Navigation.findNavController(view);

//            if (user == null) {
//                NavDirections action = HomeFragmentDirections.actionHomeFragmentToLoginFragment();
//                Navigation.findNavController(view1).navigate(action);
//                return;
//            }
//            NavDirections action = HomeFragmentDirections.actionHomeFragmentToWelcomeFragment(user);
//            Navigation.findNavController(view1).navigate(action);
        });

        view.findViewById(R.id.button_reset).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("token", null);
            editor.putString("user", null);
            editor.apply();
        });

    }
}
