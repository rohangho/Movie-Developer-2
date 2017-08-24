package com.example.android.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.movies.utilities.Json_builder;
import com.example.android.movies.utilities.Json_movie;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class Trailer_Selector extends AppCompatActivity {

    private RecyclerView rec;
    private Trailer_Adapter madapt;

    public  String mover;
    private static Bundle bun;
    private final String KEY_RECYCLER_STATE = "recycler_state";



    public String getMover() {
        return mover;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_selector);


        rec = (RecyclerView) findViewById(R.id.all_trailer_list_view);
        LinearLayoutManager layoutlManager
                = new LinearLayoutManager(this);
        madapt = new Trailer_Adapter(this);
        rec.setLayoutManager(layoutlManager);
        rec.setHasFixedSize(true);

        rec.setAdapter(madapt);
        rec.setLayoutManager(layoutlManager);
        Intent intentThatStartedThisActivity = getIntent();
        mover = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        FetchTask fb = new FetchTask();
        fb.execute();
    }

    public void onClick(String s) {

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(bun!=null)
        {   //Restore instance is being called
            Parcelable listState = bun.getParcelable(KEY_RECYCLER_STATE);
            rec.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        bun = new Bundle();
        Parcelable listState = rec.getLayoutManager().onSaveInstanceState();
        bun.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    public class FetchTask extends AsyncTask<String, Void, String[]> {

        Json_builder obj = new Json_builder();

        @Override
        protected String[] doInBackground(String... params) {


            URL RequestUrl = null;
            try {
                RequestUrl = Json_movie.buildurlfortrailer(obj.returnid(mover));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                String jsonResponse = Json_movie
                        .getResponseFromHttpUrl(RequestUrl);

                String[] simpleJsonData = Json_movie
                        .type(Trailer_Selector.this, jsonResponse);

                return simpleJsonData;

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

