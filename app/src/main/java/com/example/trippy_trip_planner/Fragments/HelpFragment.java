
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: HelpFragment
//  Description: This file is to use to implement Help Fragment

package com.example.trippy_trip_planner.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trippy_trip_planner.R;

public class HelpFragment extends Fragment {

    // Constructor
    public HelpFragment() {
        // Required empty public constructor
    }

    //	Function Name: onCreateView()
    //	Description: Inflate the layout for this fragment
    //	Return: void
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onBindViewHolder", "Method onBindViewHolder executed in HelpFragment");

        View view = inflater.inflate(R.layout.fragment_help, container, false);
        return view;
    }
}