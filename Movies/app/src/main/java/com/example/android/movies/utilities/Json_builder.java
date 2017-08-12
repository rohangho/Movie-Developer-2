package com.example.android.movies.utilities;

import android.content.Context;

import com.example.android.movies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ROHAN on 27-07-2017.
 */

public class Json_builder {

    public static int k = 0;
    public static String imageUrl = "";

    public static String[] parsedData;
    public static String[] parsedoverview;
    public static String[] parsedimage;
    public static String[] parsedrating;

    public static String[] getSimpleMovieStringsFromJson(Context context, String JsonStr)
            throws JSONException {
        ArrayList<Movie> news = new ArrayList<>();
        JSONObject forJson = new JSONObject(JsonStr);
        JSONArray getar = forJson.getJSONArray("results");
        parsedData = new String[getar.length()];
        parsedoverview = new String[getar.length()];
        parsedimage = new String[getar.length()];
        parsedrating = new String[getar.length()];
        k = getar.length();
        for (int i = 0; i < getar.length(); i++) {
            JSONObject current = getar.getJSONObject(i);
            String title = current.getString("original_title");
            String overview = current.getString("overview");
            String rat = current.getString("vote_average");
            imageUrl = "http://image.tmdb.org/t/p/w185/" + current.getString("poster_path");
            Movie newsection = new Movie(imageUrl);
            news.add(newsection);
            parsedimage[i] = imageUrl;
            parsedrating[i] = rat;
            parsedoverview[i] = overview;
            parsedData[i] = title;
        }
        return parsedData;


    }

    public String check(String a) {

        int o = 0;

        for (int j = 0; j < k; j++) {
            if (parsedData[j].equals(a))
            {o = j;}


        }
        return parsedoverview[o];
    }

    public String[] CHECKiMAGE() {
        return parsedimage;
    }

    public String checkrating(String c) {

        int q = 0;
        for (int w = 0; w < k; w++) {
            if (c.equals(parsedData[w]))
                q = w;

        }

        return parsedrating[q];
    }

    public String checkImage(String r){
        int t=0;
        for(int y=0;y<k;y++)
        {
            if(r.equals(parsedData[y]))
                t=y;
        }
        return parsedimage[t];
    }
}









