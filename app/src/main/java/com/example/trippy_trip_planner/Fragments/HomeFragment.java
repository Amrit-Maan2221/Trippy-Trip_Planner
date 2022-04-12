package com.example.trippy_trip_planner.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trippy_trip_planner.Adapter.TripAdapter;
import com.example.trippy_trip_planner.Model.Trip;
import com.example.trippy_trip_planner.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecyclerView);

        ArrayList<Trip> tripList = new ArrayList<>();
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));
        tripList.add(new Trip("Kashmir", "trip to kashmir", "21/5/2022", "9:22"));

        TripAdapter adapter = new TripAdapter(tripList, getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }
}