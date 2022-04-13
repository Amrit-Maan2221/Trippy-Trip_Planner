// Reference : https://stackoverflow.com/questions/22709751/how-to-send-notification-if-user-inactive-for-3-days

package com.example.trippy_trip_planner.Services;


import static com.example.trippy_trip_planner.AddTripActivity.PREFS;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.trippy_trip_planner.AddTripActivity;
import com.example.trippy_trip_planner.R;

public class CheckRecentRun extends Service{



    private final static String TAG = "CheckRecentPlay";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN_ONE_FOURTH = 15000L;

    //private static long delay = MILLISECS_PER_MIN_ONE_FOURTH;   // 0.5 minutes (for testing)
    private static long delay = MILLISECS_PER_DAY * 7;   // 7 days

    private static String Channel_ID = "My Channel";


    @Override
    public void onCreate() {
        super.onCreate();



        Log.v(TAG, "Service started");
        SharedPreferences settings = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Are notifications enabled?
        if (settings.getBoolean("enabled", true)) {
            // Is it time for a notification?
            if (settings.getLong("lastRun", Long.MAX_VALUE) < System.currentTimeMillis() - delay) {
                sendNotification();
            }
        } else {
            Log.i(TAG, "Notifications are disabled");
        }

        // Set an alarm for the next time this service should run:
        setAlarm();

        Log.v(TAG, "Service stopped");
        stopSelf();
    }

    public void setAlarm() {

        Intent serviceIntent = new Intent(this, CheckRecentRun.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_IMMUTABLE);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification() {
        Log.d(TAG, "Sending Notification");
        Intent mainIntent = new Intent(this, AddTripActivity.class);
        NotificationCompat.Builder mBuilder
                = new NotificationCompat.Builder(this, Channel_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("We Miss You!")
                .setContentText("You Have not planned a new Trip Since a while so Let's plan a new Trip")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(this, 131314, mainIntent,
                        PendingIntent.FLAG_IMMUTABLE))
                .setAutoCancel(true).setWhen(System.currentTimeMillis())
                .setTicker("We Miss You! Plan a new Trip.");

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "My Channel Name";
            String description = "My much larger channel description";
            @SuppressLint("WrongConstant")
            NotificationChannel channel
                    = new NotificationChannel(Channel_ID, name,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(131315, mBuilder.build());

        Log.v(TAG, "Notification sent");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
