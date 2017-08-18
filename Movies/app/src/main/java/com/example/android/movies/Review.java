package com.example.android.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.movies.utilities.Json_builder;
import com.example.android.movies.utilities.Json_review;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class Review extends AppCompatActivity {
    private RecyclerView rec;
    private Review_Adapter madapt;
    public static String mover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        rec = (RecyclerView) findViewById(R.id.recyclerview_review);
        LinearLayoutManager layoutlManager
                = new LinearLayoutManager(this);
        madapt = new Review_Adapter();
        rec.setLayoutManager(layoutlManager);
        rec.setHasFixedSize(true);

        rec.setAdapter(madapt);
        rec.setLayoutManager(layoutlManager);
        Intent intentThatStartedThisActivity = getIntent();
        mover = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        FetchTask fb = new FetchTask();
        fb.execute();
    }

    public class FetchTask extends AsyncTask<String, Void, String[]> {

        Json_builder obj = new Json_builder();

        @Override
        protected String[] doInBackground(String... params) {


            URL RequestUrl = null;
            try {
                RequestUrl = Json_review.buildurlforreview(obj.returnid(mover));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                String jsonResponse = Json_review
                        .getResponseFromHttpUrl(RequestUrl);

                String[] simpleJsonWeatherData = Json_review
                        .rev(Review.this, jsonResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] Data) {

            if (Data != null) {


                madapt.setData(Data);
            }
        }


    }
}
