package com.example.trippy_trip_planner.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trippy_trip_planner.Adapter.TripAdapter;
import com.example.trippy_trip_planner.DBoperations.DBHandler;
import com.example.trippy_trip_planner.Model.Trip;
import com.example.trippy_trip_planner.R;

import java.util.ArrayList;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<Trip> tripModalArrayList;
    private DBHandler dbHandler;
    private TripAdapter tripRVAdapter;
    private RecyclerView tripsRV;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            // initializing our all variables.
            tripModalArrayList = new ArrayList<>();
            dbHandler = new DBHandler(getContext());

            // getting our course array
            // list from db handler class.
            tripModalArrayList = dbHandler.readTrips();


            //// on below line passing our array lost to our adapter class.
            TripAdapter adapter = new TripAdapter(tripModalArrayList, getContext());


            tripsRV = view.findViewById(R.id.homeRecyclerView);


            // setting layout manager for our recycler view.
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            tripsRV.setLayoutManager(layoutManager);


            // setting our adapter to recycler view.
            tripsRV.setAdapter(adapter);


        }

        return view;
    }
}