package com.example.android.movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ROHAN on 10-08-2017.
 */

public class dbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="fav.db";
    public static final int DATABASE_VERSION=1;

    public dbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String Favourite_TABLE= "CREATE TABLE " +
                Contract.entry.TABLE_NAME+ " (" +
                Contract.entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Contract.entry.COLUMN_MOVIE_NAME + " TEXT NOT NULL "+
                ");";
        Log.i("SQl ",Favourite_TABLE);
        sqLiteDatabase.execSQL(Favourite_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP THE TABLE IF EXIST"+Contract.entry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
