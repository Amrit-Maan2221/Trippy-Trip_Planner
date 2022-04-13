package com.example.trippy_trip_planner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trippy_trip_planner.Fragments.HelpFragment;
import com.example.trippy_trip_planner.Fragments.HomeFragment;
import com.example.trippy_trip_planner.Services.CheckRecentRun;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;
    private Fragment selectorFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new HomeFragment()).commit();
                        break;
                    case R.id.help:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new HelpFragment()).commit();
                        break;
                }
                return  true;
            }});
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new HomeFragment()).commit();
    }

    public void addTripActivity(View view) {
        Intent intent = new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }


}