package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.movies.adapter.AdapterOnClickHandler;
import com.example.android.movies.utilities.Json_builder;
import com.example.android.movies.utilities.network;

import java.net.URL;

public  class MainActivity extends AppCompatActivity
        implements AdapterOnClickHandler {

    private RecyclerView mrecycle;
    private adapter madapter;
    private TextView mtextview;

    private ProgressBar mLoadingIndicator;
    //private ImageView click;
    String type="popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecycle = (RecyclerView) findViewById(R.id.recyclerview_forecast);
       // click=(ImageView)findViewById(R.id.clickme);
        mtextview = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 3);

        mrecycle.setLayoutManager(layoutManager);
        mrecycle.setHasFixedSize(true);
        madapter = new adapter(this,this);

        //madapter = new adapter(this);
       mrecycle.setAdapter(madapter);

        mrecycle.setLayoutManager(layoutManager);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loaddata("popular");

    }
    private static Bundle bun;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    @Override
    protected void onResume()
    {
        super.onResume();
        if(bun!=null)
        {   //Restore instance is being called
            Parcelable listState = bun.getParcelable(KEY_RECYCLER_STATE);
            mrecycle.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        bun = new Bundle();
        Parcelable listState = mrecycle.getLayoutManager().onSaveInstanceState();
        bun.putParcelable(KEY_RECYCLER_STATE, listState);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_page, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        // COMPLETED (2) Launch the map when the map menu item is clicked
        if (id == R.id.action_pop) {
            loaddata("popular");
           // click.setVisibility(View.GONE);
            type="popular";
            return true;
        }
        if (id == R.id.action_top) {
           // click.setVisibility(View.GONE);
            loaddata("top_rated");
            type="top_rated";
            return true;
        }
        if(id==R.id.favourite){
            Intent startnew=new Intent(this,Favourite.class);
            startActivity(startnew);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loaddata(String par) {
        new FetchTask().execute(par);
    }
    Json_builder over=new Json_builder();
    @Override
    public void onClick(String movies) {
        Context context = this;
        Class destinationClass = Detail_activity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movies);
        startActivity(intentToStartDetailActivity);

    }


    public class FetchTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;

            }


            URL RequestUrl = network.buildUrl(type);

            try {
                String jsonResponse = network
                        .getResponseFromHttpUrl(RequestUrl);

                String[] simpleJsonWeatherData = Json_builder
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] Data) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (Data != null) {
                showDataView();
                madapter.setData(Data);
            } else {
                showErrorMessage();
            }
        }


    }

    private void showDataView() {
        mtextview.setVisibility(View.INVISIBLE);
        mrecycle.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mrecycle.setVisibility(View.INVISIBLE);
        mtextview.setVisibility(View.VISIBLE);

    }
}
