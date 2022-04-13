package com.example.trippy_trip_planner.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryLowReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction()))
        {
            Toast.makeText(context, "You Might be Going to a Trip Soon! Recharge Your Phone!!!", Toast.LENGTH_SHORT).show();
        }
    }
}