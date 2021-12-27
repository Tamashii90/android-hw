package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wmshw.model.AuthRequest;
import com.example.wmshw.model.JwtResponse;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    EditText usernameField;
    EditText passwordField;
    ProgressBar progressBar;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameField = view.findViewById(R.id.edit_text_username);
        passwordField = view.findViewById(R.id.edit_text_password);
        progressBar = view.findViewById(R.id.progressBar);

        view.findViewById(R.id.button_confirm).setOnClickListener(this::login);
    }

    public void login(View view) {
        MyUtils.hideKeyboard(view);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (MyUtils.hasEmptyString(username, password)) {
            Toast.makeText(getActivity(), "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRequest authRequest = new AuthRequest(username, password);

        Call<JwtResponse> call = MyApi.instance.postLogin(authRequest);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful()) {
                    JwtResponse jwtResponse = response.body();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", jwtResponse.getJwt());
                    editor.putString("authority", jwtResponse.getAuthority());
                    editor.putString("user", username);
                    if (jwtResponse.getAuthority().equals("USER")) {
                        editor.putString("plateNumber", password);
                    }
                    editor.apply();
                    progressBar.setVisibility(View.INVISIBLE);
                    if (jwtResponse.getAuthority().equals("ADMIN")) {
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                    } else if (jwtResponse.getAuthority().equals("USER")) {
                        startActivity(new Intent(getActivity(), UserActivity.class));
                    }
                } else {
                    Toast.makeText(getContext(), MyApi.getErrorMessage(response), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}