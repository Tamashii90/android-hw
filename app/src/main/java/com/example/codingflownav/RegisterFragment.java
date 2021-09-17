package com.example.codingflownav;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText emailField = view.findViewById(R.id.edit_text_register_username);
        EditText passwordField = view.findViewById(R.id.edit_text_register_password);
        EditText passwordRepeatField = view.findViewById(R.id.edit_text_register_repeat_password);
        ProgressBar progressBar = view.findViewById(R.id.progressBar_2);

        view.findViewById(R.id.button_register).setOnClickListener(view1 -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String passwordRepeat = passwordRepeatField.getText().toString();

            if (!password.equals(passwordRepeat)) {
                Toast.makeText(getContext(), "Passwords don't match.", Toast.LENGTH_LONG).show();
                return;
            }


            RegisterRequest registerRequest = new RegisterRequest(email, password, passwordRepeat);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MyApi myApi = retrofit.create(MyApi.class);

            Call<JwtResponse> call = myApi.postRegister(registerRequest);
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
                        startActivity(new Intent(getActivity(), WorkflowActivity.class));
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