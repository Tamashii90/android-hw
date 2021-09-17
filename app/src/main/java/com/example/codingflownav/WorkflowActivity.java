package com.example.codingflownav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.security.PrivateKey;

public class WorkflowActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", null);
//        if (user == null) {
//            startActivity(new Intent(this, MainActivity.class));
//        }
    }

    public void resetApp(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", null);
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
    }
}