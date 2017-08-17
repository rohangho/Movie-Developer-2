package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.utilities.Json_builder;
import com.example.android.movies.utilities.Json_movie;
import com.example.android.movies.utilities.Json_review;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class Detail_activity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    public static String mover;
    private TextView moverviewDisplay;
    private TextView ratingdisplat;
    private ImageView img;
    private TextView moviename;
    private TextView review;
    String key = null;
    // ArrayList<String> checkDupli = new ArrayList<>();
    String nameofmovie;
    Json_review objrev = new Json_review();

    int e = 0;
    Json_builder over = new Json_builder();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        moviename = (TextView) findViewById(R.id.name);
        review = (TextView) findViewById(R.id.review1);
        moverviewDisplay = (TextView) findViewById(R.id.storyline);
        ratingdisplat = (TextView) findViewById(R.id.rating);
        img = (ImageView) findViewById(R.id.poster);
        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mover = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                moviename.setText((mover));

                String id = over.returnid(mover);
                try {
                    review.setText(objrev.buildurlforreview(id));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


                moverviewDisplay.setText(over.check(mover));
                ratingdisplat.setText(over.checkrating(mover));
                Picasso.with(context).load(over.checkImage(mover)).into(img);


            }
        }
        dbHelper db = new dbHelper(this);
        mDb = db.getWritableDatabase();


    }

    public void addlist(View view) {
        //  int count=0;
        Intent startnew = new Intent(this, Favourite.class);
        startActivity(startnew);
        nameofmovie = moviename.getText().toString();

        //checkDupli.add(nameofmovie);
        //for(String listOfmovies:checkDupli) {

//            if (listOfmovies.equals(nameofmovie)) {
        //              count = count + 1;
        //        }

        //  }

        //if(count==1)
        {
            addNew(nameofmovie);
        }

    }

    public void share(View view) {
        String title = "I am interested in watching " + moviename.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Lets Watch")
                .setText(title)
                .startChooser();

    }

    Json_movie objofJsonmovie = new Json_movie();

    public void atchTrailer(View view) {



        FetchTask fetchTask = new FetchTask();
        fetchTask.execute();



    }

    public class FetchTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            //if (params.length == 0) {
            //  return null;

            //}


            URL RequestUrl = null;
            try {
                RequestUrl = Json_movie.buildurlfortrailer(over.returnid(mover));
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }


            try {
                String jsonResponse = Json_movie
                        .getResponseFromHttpUrl(RequestUrl);

                String simpleJsonWeatherData = Json_movie
                        .key(Detail_activity.this, jsonResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String Data) {
            if (Data != null) {
                key = Data;
                String url = "https://www.youtube.com/watch?v=" + key;

                Uri webpage = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }


            }


        }
    }


        private long addNew(String s) {
            ContentValues cv = new ContentValues();

            cv.put(Contract.entry.COLUMN_MOVIE_NAME, s);
            return mDb.insert(Contract.entry.TABLE_NAME, null, cv);

        }


}
