package com.example.wmshw;

import android.content.Context;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

import static com.example.wmshw.MyUtils.resetApp;

public class AdminActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Set<Integer> topLevelFrags = new HashSet<>();
        topLevelFrags.add(R.id.adminSearchFragment);
        topLevelFrags.add(R.id.vehiclesFragment);
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_2);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelFrags).build();

        setSupportActionBar(findViewById(R.id.toolbar2));
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        BottomNavigationView bottomView = findViewById(R.id.admin_bottom_nav);
        NavigationUI.setupWithNavController(bottomView, navController);
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
                resetApp(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


}