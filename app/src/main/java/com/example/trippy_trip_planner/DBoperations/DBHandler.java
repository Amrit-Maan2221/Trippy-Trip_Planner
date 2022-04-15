//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: DBHandler
//  Description: This file is to use to implement Database handler logic for our Trip planner app

//Reference: https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/

package com.example.trippy_trip_planner.DBoperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.trippy_trip_planner.Model.Trip;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "tripsDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "myTrips";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String LOCATION_COL = "location";

    // below variable for our course description column.
    private static final String DATE_COL = "date";

    // below variable is for our course tracks column.
    private static final String TIME_COL = "time";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //	Function Name: onCreate()
    //	Description: this method is for creating a database by running a sqlite query
    //	Return: void
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate", "Method onCreate executed in DBHandler");

        try {
            // on below line we are creating
            // an sqlite query and we are
            // setting our column names
            // along with their data types.
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT,"
                    + LOCATION_COL + " TEXT,"
                    + DATE_COL + " TEXT,"
                    + TIME_COL + " TEXT)";

            // at last we are calling a exec sql
            // method to execute above sql query
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	Function Name: addNewTrip()
    //	Description: this method is use to add new course to our sqlite database.
    //	Return: void
    public void addNewTrip(String tripName, String tripLocation, String tripDate, String tripTime) {
        Log.d("addNewTrip", "Method addNewTrip executed in DBHandler");

        try {
            // on below line we are creating a variable for
            // our sqlite database and calling writable method
            // as we are writing data in our database.
            SQLiteDatabase db = this.getWritableDatabase();

            // on below line we are creating a
            // variable for content values.
            ContentValues values = new ContentValues();

            // on below line we are passing all values
            // along with its key and value pair.
            values.put(NAME_COL, tripName);
            values.put(LOCATION_COL, tripLocation);
            values.put(DATE_COL, tripDate);
            values.put(TIME_COL, tripTime);

            // after adding all values we are passing
            // content values to our table.
            db.insert(TABLE_NAME, null, values);

            // at last we are closing our
            // database after adding database.
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	Function Name: onUpgrade()
    //	Description: this method will execute when database is upgraded
    //	Return: void
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "Method onUpgrade executed in DBHandler");

        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //	Function Name: readTrips()
    //	Description: this method is used for reading all the courses.
    //	Return: void
    public ArrayList<Trip> readTrips() {
        Log.d("readTrips", "Method readTrips executed in DBHandler");

        try {
            // on below line we are creating a
            // database for reading our database.
            SQLiteDatabase db = this.getReadableDatabase();

            // on below line we are creating a cursor with query to read data from database.
            Cursor cursorTrips = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            // on below line we are creating a new array list.
            ArrayList<Trip> tripModalArrayList = new ArrayList<>();

            // moving our cursor to first position.
            if (cursorTrips.moveToFirst()) {
                do {
                    // on below line we are adding the data from cursor to our array list.
                    tripModalArrayList.add(new Trip(cursorTrips.getString(2),
                            cursorTrips.getString(1),
                            cursorTrips.getString(3),
                            cursorTrips.getString(4)));
                } while (cursorTrips.moveToNext());
                // moving our cursor to next.
            }
            // at last closing our cursor
            // and returning our array list.
            cursorTrips.close();
            return tripModalArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

