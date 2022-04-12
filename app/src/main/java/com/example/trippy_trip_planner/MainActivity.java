package com.example.trippy_trip_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTripActivity(View view) {
        Intent intent = new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }
}