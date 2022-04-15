
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: BatteryLowReceiver
//  Description: This file is to use to implement logic for Battery low Broadcast receiver


package com.example.trippy_trip_planner.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryLowReceiver extends BroadcastReceiver {

    //	Function Name: onReceive()
    //	Description: execute when Broadcast received
    //	Return: void
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive", "Method onReceive executed in Battery Low Broadcast receiver");

        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction()))
        {
            Toast.makeText(context, "You Might be Going to a Trip Soon! Recharge Your Phone!!!", Toast.LENGTH_SHORT).show();
        }
    }
}