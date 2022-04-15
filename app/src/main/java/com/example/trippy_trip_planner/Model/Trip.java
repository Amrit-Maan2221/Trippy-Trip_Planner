
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: Trip
//  Description: This file is to use to define model for Trip

package com.example.trippy_trip_planner.Model;

public class Trip {

    // Private data members for Trip model
    private String tripLocation;
    private String tripName;
    private String tripDate;
    private String tripTime;

    // Constructor
    public Trip(String tripLocation, String tripName, String tripDate, String tripTime) {
        this.tripLocation = tripLocation;
        this.tripName = tripName;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
    }

    // Public Properties for Trip model

    public String getTripLocation() {
        return tripLocation;
    }

    public void setTripLocation(String tripLocation) {
        this.tripLocation = tripLocation;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }
}
