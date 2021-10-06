package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashSet;
import java.util.Set;

public class UserActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NavController navController;
    AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        Set<Integer> topLevelFrags = new HashSet<>();
        topLevelFrags.add(R.id.userFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_3);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelFrags).build();

        setSupportActionBar(findViewById(R.id.toolbar_user));
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
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