package com.example.trippy_trip_planner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class AddTripActivity extends AppCompatActivity {

    private ImageView close;
    private TextView addTrip;
    private TextView tripLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        close = findViewById(R.id.close);
        addTrip = findViewById(R.id.add);
        tripLocation = findViewById(R.id.tripLocation);

        if(!Places.isInitialized()) {
            //Initilize Places
            Places.initialize(getApplicationContext(), "AIzaSyBSl9nygYVuyjhOK0uXfmhnjr6598uKb14");
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

            }
        });


    }
}