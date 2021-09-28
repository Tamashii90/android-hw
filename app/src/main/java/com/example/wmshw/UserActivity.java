package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        setSupportActionBar(findViewById(R.id.toolbar_user));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_log_out:
                resetApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void resetApp() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", null);
        editor.putString("token", null);
        editor.putString("authority", null);
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
    }
}