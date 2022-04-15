//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: TripAdapter
//  Description: This file is to use to implement Adapter logic for our Trip planner app


package com.example.trippy_trip_planner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippy_trip_planner.Model.Trip;
import com.example.trippy_trip_planner.R;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.viewHolder> {

    ArrayList<Trip> tripList;
    Context context;

    // Constructor
    public TripAdapter(ArrayList<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    //	Function Name: onCreateViewHolder()
    //	Description: inflate the view on Create View Holder
    //	Return: viewHolder
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "Method onCreateViewHolder executed in TripAdapter");
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.trips_list_recycler_layout, parent, false);
            return new viewHolder(view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //	Function Name: onBindViewHolder()
    //	Description: Bind the view holder
    //	Return: void
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Log.d("onBindViewHolder", "Method onBindViewHolder executed in TripAdapter");

        Trip trip = tripList.get(position);
        holder.tripName.setText(trip.getTripName());
        holder.tripDate.setText(trip.getTripDate());
        holder.tripTime.setText(trip.getTripTime());
        holder.btnTripLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String geoUri = "http://maps.google.com/maps?q=" + trip.getTripLocation();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //	Function Name: getItemCount()
    //	Description: Get the number of trips
    //	Return: Integer - Number of trips
    @Override
    public int getItemCount() {
        return tripList.size();
    }

    // Custom Class viewHolder to define view holder we are using in the Adapter
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tripName, tripDate, tripTime;
        Button btnTripLocation;

        // Constructor
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("viewHolder", "View holder object created");

            try {
                tripName = itemView.findViewById(R.id.tvrTripName);
                tripDate = itemView.findViewById(R.id.tvrTripDate);
                tripTime = itemView.findViewById(R.id.tvrTripTime);
                btnTripLocation = itemView.findViewById(R.id.btnStartTrip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
