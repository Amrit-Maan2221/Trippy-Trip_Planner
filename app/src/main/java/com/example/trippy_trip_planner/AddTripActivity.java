package com.example.trippy_trip_planner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippy_trip_planner.DBoperations.DBHandler;
import com.example.trippy_trip_planner.Services.CheckRecentRun;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.Arrays;
import java.util.List;

public class AddTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ImageView close;
    private TextView addTrip, tripLocation, tripDate, tripTime;
    private EditText tripName;
    private DBHandler dbHandler;

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddTripActivity.this);

        close = findViewById(R.id.close);
        addTrip = findViewById(R.id.add);
        tripLocation = findViewById(R.id.tripLocation);
        tripName = findViewById(R.id.tripName);
        tripDate = findViewById(R.id.tripDate);
        tripTime = findViewById(R.id.tripTime);



        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        // First time running app?
        if (!settings.contains("lastRun")){
            enableNotification(null);
        } else{
            recordRunTime();
        }

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this,  CheckRecentRun.class));



        if(!Places.isInitialized()) {
            //Initilize Places
            Places.initialize(getApplicationContext(), "AIzaSyCBNkqTT6NGNIPzwiZKHmA7qOVXqBTIHhA");
        }





        // Using Intent
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Place place = Autocomplete.getPlaceFromIntent(data);
                            tripLocation.setText(place.getName());
                        }
                    }
                });





        tripLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set the fields to specify which types of place data to
                // return after the user has made a selection.

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(AddTripActivity.this);
                someActivityResultLauncher.launch(intent);

            }
        });



        tripDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });



        tripTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                showTimeDialog();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTripActivity.this, MainActivity.class));
                finish();
            }
        });

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // below line is to get data from all edit text fields.
                String strTripName = tripName.getText().toString();
                //String strTripLocation = tripLocation.getText().toString();
                String strTripLocation = "Kashmir";
                String strTripDate = tripDate.getText().toString();
                String strTripTime = tripTime.getText().toString();

                // validating if the text fields are empty or not.
                if (strTripName.isEmpty() || strTripLocation.isEmpty() || (strTripDate == "Select Date") || (strTripTime == "Select Start Time")) {
                    Toast.makeText(AddTripActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewTrip(strTripName, strTripLocation, strTripDate, strTripTime);
                // after adding the data we are displaying a toast message.
                Toast.makeText(AddTripActivity.this, "Trip has been added.", Toast.LENGTH_SHORT).show();
                tripName.setText("");
                tripLocation.setText("");
                tripDate.setText("Select Date");
                tripTime.setText("Select Trip Time");
                startActivity(new Intent(AddTripActivity.this, MainActivity.class));
                finish();


            }
        });
    }



    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = month + "/" + dayOfMonth + "/" + year;
        tripDate.setText(date);
    }

    private void showTimeDialog() {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                tripTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(AddTripActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }


    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
        Log.d(TAG, "Notifications enabled");
    }

    public void recordRunTime() {
        try {
            Log.d(TAG, "Recording run time");
            editor.putLong("lastRun", System.currentTimeMillis());
            editor.commit();
        }
        catch (Exception e) {
            Log.d("Exception", "Unable to record the runtime");
        }
    }

}