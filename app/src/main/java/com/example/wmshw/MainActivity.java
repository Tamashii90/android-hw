package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String authority = sharedPreferences.getString("authority", null);
        if ("ADMIN".equals(authority)) {
            startActivity(new Intent(this, AdminActivity.class));
            return;
        } else if ("USER".equals(authority)) {
            startActivity(new Intent(this, UserActivity.class));
            return;
        }
    }
}