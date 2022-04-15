//  Developers: Amritpal Singh, Gursharan Singh, Waqar Ali Saleemi, Mustafa Efiloglu
//  Group: Group 10
//  Project Name: Trippy-Trip_Planner
//  Date: 13 April, 2022
//  File Name: DBContentProvider
//  Description: This file is to use to implement the logic for DBContentProvider


package com.example.trippy_trip_planner.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class DBContentProvider extends ContentProvider {

    // Constructor
    public DBContentProvider() {
    }

    // defining authority so that other application can access it
    static final String PROVIDER_NAME = "com.example.trippy_trip_planner.ContentProvider";

    // defining content URI
    static final String URL = "content://" + PROVIDER_NAME + "/trips";

    // parsing the content URI
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String id = "id";
    public static final String name = "name";
    public static String date = "date";
    public static final String location = "location";
    public static final String time = "time";

    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    // Static class members
    static {

        // to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // to access whole table
        uriMatcher.addURI(PROVIDER_NAME, "trips", uriCode);

        // to access a particular row
        // of the table
        uriMatcher.addURI(PROVIDER_NAME, "trips/*", uriCode);
    }

    //	Function Name: onCreate()
    //	Description: this method will execute on creation of the database
    //	Return: boolean - true when success
    @Override
    public boolean onCreate() {
        Log.d("onCreate", "Method onCreate executed in DBContentProvider to create database");

        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    //	Function Name: insert()
    //	Description: this method will execute on when new record is going to be inserted
    //	Return: Uri to the record inserted
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("insert", "Method insert executed in DBContentProvider to insert new record");

        try {
            long rowID = db.insert(TABLE_NAME, "", values);
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
            throw new SQLiteException("Failed to add a record into " + uri);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLiteException("Failed to add a record into " + uri);
        }
    }

    //	Function Name: query()
    //	Description: this method will execute to parse the query and return the cursor
    //	Return: Cursor contains the current row pointer
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("query", "Method onBindViewHolder executed in DBContentProvider");

        try {
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(TABLE_NAME);
            switch (uriMatcher.match(uri)) {
                case uriCode:
                    qb.setProjectionMap(values);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            if (sortOrder == null || sortOrder == "") {
                sortOrder = id;
            }
            Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                    null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //	Function Name: getType()
    //	Description: this method will execute to return the type of the item for the provided Uri
    //	Return: String contains the type information
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/trips";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    //	Function Name: delete()
    //	Description: this method will execute to delete a record
    //	Return: int for the row number deleted
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    //	Function Name: update()
    //	Description: this method will execute to update a record
    //	Return: int for the row number update
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    // creating object of database
    // to perform query
    private SQLiteDatabase db;

    // declaring name of the database
    static final String DATABASE_NAME = "tripsDB";

    // declaring table name of the database
    static final String TABLE_NAME = "myTrips";

    // declaring version of the database
    static final int DATABASE_VERSION = 1;


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

    // sql query to create the table
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + LOCATION_COL + " TEXT,"
            + DATE_COL + " TEXT,"
            + TIME_COL + " TEXT);";

    // Database helper class used in DBContentProvider
    // creating a database
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // defining a constructor
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //	Function Name: onCreate()
        //	Description:This method will execute in creating a table in the database
        //	Return: void
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        //	Function Name: onUpgrade()
        //	Description:This method will execute while upgrading a table in the database
        //	Return: void
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // sql query to drop a table
            // having similar name
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}