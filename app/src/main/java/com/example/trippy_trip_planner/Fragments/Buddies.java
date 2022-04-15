
//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: Buddies
//  Description: This file is to use to implement Buddies fragment that help us view our contacts

//Reference: Week 10 Class Examples
//          https://www.jackrutorial.com/2018/06/android-request-contact-permission.html

package com.example.trippy_trip_planner.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.trippy_trip_planner.R;

public class Buddies extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public TextView outputText;

    // Constructor
    public Buddies() {
        // Required empty public constructor
    }

    //	Function Name: onCreateView()
    //	Description: Inflate the layout for this fragment
    //	Return: View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "Method onCreateView executed in Buddies fragment");

        View view = inflater.inflate(R.layout.fragment_buddies, container, false);
        outputText = (TextView) view.findViewById(R.id.tvContacts);
        requestContactPermission();

        return view;
    }

    //	Function Name: requestContactPermission()
    //	Description: This function is used to request contact access permissions
    //	Return: void
    public void requestContactPermission() {
        Log.d("requestContactPerms", "Method requestContactPermission executed in Buddies fragment");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            android.Manifest.permission.READ_CONTACTS)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Read Contacts permission");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.setMessage("Please enable access to contacts.");
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                requestPermissions(
                                        new String[]
                                                {android.Manifest.permission.READ_CONTACTS}
                                        , PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        });
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{android.Manifest.permission.READ_CONTACTS},
                                PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                } else {
                    getContacts();
                }
            } else {
                getContacts();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	Function Name: getContacts()
    //	Description: This function is used to get contacts to ipmplement System Content Provider
    //	Return: void
    @SuppressLint("Range")
    public void getContacts() {
        Log.d("getContacts", "Method getContacts executed in Buddies fragment");

        String phoneNumber = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        try {
            // Loop for every contact in the phone
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                    int i = 0;
                    if (hasPhoneNumber > 0) {
                        output.append("\n" + name);
                        // Query and loop for every phone number of the contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            output.append(": " + phoneNumber);
                        }
                        phoneCursor.close();
                        i++;
                    }
                    output.append("\n");
                }
                outputText.setText(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}