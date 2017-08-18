package com.example.android.movies.utilities;

import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ROHAN on 14-08-2017.
 */

public class Json_movie {

    public static URL buildurlfortrailer(String id) throws JSONException, MalformedURLException {
        String url = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a481f1eb249ec2fb6a0466aa51354515";
        Uri builtUri = Uri.parse(url).buildUpon().build();
        URL url1 = null;
        url1 = new URL(builtUri.toString());

        return url1;
    }
        public static String key(Context context, String url) throws JSONException {

        JSONObject forJson = new JSONObject(url);
        JSONArray getar = forJson.getJSONArray("results");
        JSONObject current = getar.getJSONObject(1);
        String key = current.getString("key");

        return key;
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

