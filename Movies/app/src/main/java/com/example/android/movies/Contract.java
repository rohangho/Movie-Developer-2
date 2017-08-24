package com.example.android.movies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ROHAN on 09-08-2017.
 */

public class Contract {

    public static final String AUTHORITY = "com.example.android.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "favourite";

    public static final class entry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_MOVIE_NAME = "moviename";
        public static final String COLUMN_ID="id";

    }

}
