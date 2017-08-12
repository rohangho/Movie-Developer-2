package com.example.android.movies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
/**
 * Created by ROHAN on 27-07-2017.
 */

public class network {
    private static final String TAG = network.class.getSimpleName();

    private static final String DYNAMIC_URL =
            "http://api.themoviedb.org/3/movie/";
    private static final String FORECAST_BASE_URL = DYNAMIC_URL;


    public static URL buildUrl(String type) {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendPath(type)
                .appendQueryParameter("api_key","a481f1eb249ec2fb6a0466aa51354515")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("URL: ", builtUri.toString());
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
