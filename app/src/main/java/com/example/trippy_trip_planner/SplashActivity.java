
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: SplashActivity
//  Description: This file is to use to implement the logic for Splash screen for Trip planner app

package com.example.trippy_trip_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    //	Function Name: onCreate()
    //	Description: inflate the view on Create
    //	Return: void
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "Welcome to App");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //We will start the main activity using Thread
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}