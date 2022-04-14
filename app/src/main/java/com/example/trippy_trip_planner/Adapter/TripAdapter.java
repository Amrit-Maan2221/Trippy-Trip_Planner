package com.example.trippy_trip_planner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public TripAdapter(ArrayList<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trips_list_recycler_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tripName.setText(trip.getTripName());
        holder.tripDate.setText(trip.getTripDate());
        holder.tripTime.setText(trip.getTripTime());
        holder.btnTripLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String geoUri = "http://maps.google.com/maps?q=" + trip.getTripLocation();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tripName, tripDate, tripTime;
        Button btnTripLocation;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.tvrTripName);
            tripDate = itemView.findViewById(R.id.tvrTripDate);
            tripTime = itemView.findViewById(R.id.tvrTripTime);
            btnTripLocation = itemView.findViewById(R.id.btnStartTrip);

        }
    }
}
