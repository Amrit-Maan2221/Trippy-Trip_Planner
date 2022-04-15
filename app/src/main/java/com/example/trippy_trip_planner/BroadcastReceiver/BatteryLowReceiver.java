
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: BatteryLowReceiver
//  Description: This file is to use to implement logic for Battery low Broadcast receiver


package com.example.trippy_trip_planner.BroadcastReceiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryLowReceiver extends BroadcastReceiver {

    //	Function Name: onReceive()
    //  Parameters: Context context, Intent intent
    //	Description: execute when Broadcast received to give warning that your battery is low
    //	Return: void
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive", "Method onReceive executed in Battery Low Broadcast receiver");

        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction()))
        {
//            If the User Battery is Low a warning is giving using a dialog that “
//            You Might be Going to a Trip Soon! Recharge Your Phone!!!
//            “ to warn  user to recharge there phone because their phone might be used in Trip and they may be unable to recharge battery

            new AlertDialog.Builder(context)
                    .setMessage("You Might be Going to a Trip Soon! Recharge Your Phone!!!")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do Nothing
                        }
                    })
                    .show();
        }
    }
}