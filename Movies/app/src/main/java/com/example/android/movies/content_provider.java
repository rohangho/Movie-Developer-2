package com.example.android.movies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.movies.Contract.entry.TABLE_NAME;

/**
 * Created by ROHAN on 16-08-2017.
 */

public class content_provider extends ContentProvider {

    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH, TASKS);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH + "/#", TASK_WITH_ID);

        return uriMatcher;
    }
    private dbHelper mTaskDbHelper;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new dbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {

            case TASKS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match) {
            case TASKS:

                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(Contract.entry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int tasksDeleted; // starts as 0
        switch (match) {
            case TASK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }


        return tasksDeleted;
    }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
