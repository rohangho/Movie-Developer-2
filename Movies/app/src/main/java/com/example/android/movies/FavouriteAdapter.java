package com.example.android.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ROHAN on 10-08-2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {

    private Context mContext;
    private Cursor mcursor;

    public FavouriteAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        // COMPLETED (10) Set the local mCount to be equal to count
        this.mcursor=cursor;

    }


    @Override
    public FavouriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.favouritelist, parent, false);
        return new FavouriteHolder(view);




    }

    @Override
    public void onBindViewHolder(FavouriteHolder holder, int position) {
        if(!mcursor.moveToPosition(position))
            return;
        String name=mcursor.getString(mcursor.getColumnIndex(Contract.entry.COLUMN_MOVIE_NAME));
        holder.nameTextView.setText(name);
    }



    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }

    class FavouriteHolder extends RecyclerView.ViewHolder {


        TextView nameTextView;
        TextView partySizeTextView;

        public FavouriteHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.favmon);
        }

    }

}
