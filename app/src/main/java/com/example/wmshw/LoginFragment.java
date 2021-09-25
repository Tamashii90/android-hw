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
import com.example.wmshw.retrofit.MyApi;
import com.example.wmshw.retrofit.MyApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText usernameField = view.findViewById(R.id.edit_text_username);
        EditText passwordField = view.findViewById(R.id.edit_text_password);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        view.findViewById(R.id.button_confirm).setOnClickListener(view1 -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

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
                        editor.putString("user", username);
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