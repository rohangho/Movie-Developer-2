package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class Favourite extends AppCompatActivity  {

    private FavouriteAdapter mAdapter;
    private SQLiteDatabase mDb;

    RecyclerView movielistRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        movielistRecyclerView = (RecyclerView) this.findViewById(R.id.all_favourites_list_view);
        movielistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DbHelper dHelper = new DbHelper(this);
        mDb = dHelper.getWritableDatabase();
        final Cursor cursor = getAllName();

        mAdapter = new FavouriteAdapter(this, cursor);
        movielistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

               int position=viewHolder.getAdapterPosition();
                cursor.moveToPosition(position);
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String stringId=Integer.toString(id);

                Log.i("i am string",stringId);
                Uri uri = Contract.entry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                getContentResolver().delete(uri, null, null);

            }
        }).attachToRecyclerView(movielistRecyclerView);

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



    public void onClick(String movies) {
        Context context = this;
        Class destinationClass = Detail_activity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movies);
        startActivity(intentToStartDetailActivity);

    }


}
