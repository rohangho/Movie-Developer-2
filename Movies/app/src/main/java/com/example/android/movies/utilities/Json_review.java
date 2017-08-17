package com.example.android.movies.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ROHAN on 14-08-2017.
 */

public class Json_review {

    public String buildurlforreview(String id) throws JSONException {
        String url="http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=a481f1eb249ec2fb6a0466aa51354515";
        Log.i("SIMMYYYYYYYYYYYYYYYY",url);
        JSONObject forJson = new JSONObject(url);
        JSONArray getar = forJson.getJSONArray("results");
        JSONObject current = getar.getJSONObject(1);

        String review = current.getString("content");
        Log.i("SIMMYYYYYYYYYYYYYYYY",review);

        return review;
    }

}
