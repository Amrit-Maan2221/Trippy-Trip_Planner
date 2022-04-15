// Reference : https://stackoverflow.com/questions/22709751/how-to-send-notification-if-user-inactive-for-3-days

package com.example.trippy_trip_planner;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trippy_trip_planner.BroadcastReceiver.BatteryLowReceiver;
import com.example.trippy_trip_planner.Fragments.Buddies;
import com.example.trippy_trip_planner.Fragments.HelpFragment;
import com.example.trippy_trip_planner.Fragments.HomeFragment;
import com.example.trippy_trip_planner.Fragments.PresentersFragment;
import com.example.trippy_trip_planner.Services.CheckRecentRun;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;
    BatteryLowReceiver batteryLowReceiver = new BatteryLowReceiver();



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
                        Log.d("Menu", "Menu changed to schedule");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new HomeFragment()).commit();
                        break;
                    case R.id.help:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new HelpFragment()).commit();
                        break;
                    case R.id.aboutUs:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new PresentersFragment()).commit();
                        break;
                    case R.id.buddies:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutReplace , new Buddies()).commit();
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BatteryManager.EXTRA_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryLowReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryLowReceiver);
    }
}