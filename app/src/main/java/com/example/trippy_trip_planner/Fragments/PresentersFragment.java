package com.example.trippy_trip_planner.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trippy_trip_planner.R;
import android.app.AlertDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class PresentersFragment extends Fragment {

    public PresentersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presenters, container, false);
        TextView tvAboutUs = view.findViewById(R.id.tvAboutUs);

        new AlertDialog.Builder(getContext())
                .setMessage("Would you like to know about Us?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FileOutputStream fOut = getContext().openFileOutput("AboutUsFile", MODE_PRIVATE);
                            String fileContents = "We Students from Conestoga College Developed this App " +
                                    "for Our Mobile Application Development Course for Assignment 2\n\n" +
                                    "Developers:\n" +
                                    "Amritpal Singh\n" +
                                    "Gursharan Singh\n" +
                                    "Waqar Ali Saleemi\n" +
                                    "Mustafa Efiloglu";
                            fOut.write(fileContents.getBytes());
                            fOut.close();

                            File fileDir = new File(getContext().getFilesDir(), "AboutUsFile");
                            Log.d("FileWorking", "File Saved At "+ fileDir);


                            FileInputStream fIn = getContext().openFileInput("AboutUsFile");
                            int c;
                            String temp = "";

                            while((c=fIn.read()) != -1)
                            {
                                temp = temp  + Character.toString((char) c);
                            }
                            tvAboutUs.setText(temp);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvAboutUs.setText("You denied to know about Us...");
                    }
                }).show();




        return view;
    }
}