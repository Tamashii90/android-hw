package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

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
        Set<Integer> topLevelFrags = new HashSet<>();
        topLevelFrags.add(R.id.loginFragment);
        topLevelFrags.add(R.id.registerFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelFrags).build();

        setSupportActionBar(findViewById(R.id.toolbar));
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        BottomNavigationView bottomView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomView, navController);
    }
}