package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.utilities.Json_builder;
import com.squareup.picasso.Picasso;

public class Detail_activity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    public static String mover;
    private TextView moverviewDisplay;
    private TextView ratingdisplat;
    private ImageView img;
    private TextView moviename;
    Json_builder over=new Json_builder();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        moviename=(TextView)findViewById(R.id.name);
        moverviewDisplay = (TextView) findViewById(R.id.storyline);
        ratingdisplat=(TextView)findViewById(R.id.rating);
        img=(ImageView)findViewById(R.id.poster);
        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mover = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                moviename.setText((mover));

                moverviewDisplay.setText(over.check(mover));
                ratingdisplat.setText(over.checkrating(mover));
                Picasso.with(context).load(over.checkImage(mover)).into(img);


            }
        }
        dbHelper db=new dbHelper(this);
        mDb=db.getWritableDatabase();


    }

    public void addlist(View view){
        Intent startnew=new Intent(this,Favourite.class);
        startActivity(startnew);
        addNew(moviename.getText().toString());

    }

    private long addNew(String s) {
        ContentValues cv=new ContentValues();
        cv.put(Contract.entry.COLUMN_MOVIE_NAME,s);
        return mDb.insert(Contract.entry.TABLE_NAME,null,cv);

    }

}
