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
import com.example.wmshw.retrofit.JwtResponse;
import com.example.wmshw.retrofit.MyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText emailField = view.findViewById(R.id.edit_text_register_username);
        EditText passwordField = view.findViewById(R.id.edit_text_register_password);
        EditText repeatPasswordField = view.findViewById(R.id.edit_text_register_repeat_password);
        ProgressBar progressBar = view.findViewById(R.id.progressBar_2);

        view.findViewById(R.id.button_register).setOnClickListener(view1 -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String repeatPassword = repeatPasswordField.getText().toString();

            if (!password.equals(repeatPassword)) {
                Toast.makeText(getContext(), "Passwords don't match.", Toast.LENGTH_LONG).show();
                return;
            }

            RegisterRequest registerRequest = new RegisterRequest(email, password, repeatPassword);

            Call<JwtResponse> call = MyApi.instance.postRegister(registerRequest);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<JwtResponse>() {
                @Override
                public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                    if (response.isSuccessful()) {
                        JwtResponse jwtResponse = response.body();
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("token", jwtResponse.getJwt());
                        editor.putString("user", email);
                        editor.apply();
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                    } else {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<JwtResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        });
    }
}