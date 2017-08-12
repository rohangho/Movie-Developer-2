package com.example.android.movies;

import android.provider.BaseColumns;

/**
 * Created by ROHAN on 09-08-2017.
 */

public class Contract {

    public static final class entry implements BaseColumns{

        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_MOVIE_NAME = "moviename";

    }

}
