package com.example.android.movies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Favourite extends AppCompatActivity {

    private FavouriteAdapter mAdapter;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        RecyclerView movielistRecyclerView;
        movielistRecyclerView = (RecyclerView) this.findViewById(R.id.all_favourites_list_view);
        movielistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper dHelper = new dbHelper(this);
        mDb = dHelper.getWritableDatabase();
        Cursor cursor = getAllName();

        mAdapter = new FavouriteAdapter(this, cursor);
        movielistRecyclerView.setAdapter(mAdapter);

    }

    private Cursor getAllName() {
        return mDb.query(
                Contract.entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.entry.COLUMN_MOVIE_NAME
        );
    }
}
