package com.example.wmshw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class WorkflowActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);
//        if (user == null) {
//            startActivity(new Intent(this, MainActivity.class));
//        }
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_2);
        navController = navHostFragment.getNavController();

        setSupportActionBar(findViewById(R.id.toolbar2));
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_2, menu);
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
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
    }

}