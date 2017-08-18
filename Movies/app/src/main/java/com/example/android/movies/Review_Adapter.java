package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ROHAN on 18-08-2017.
 */

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.AdapterViewHolder> {
    private String[] mdata;
    @Override
    public Review_Adapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.reviewlist;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }

    public void setData(String[] Data) {
        mdata = Data;

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Review_Adapter.AdapterViewHolder holder, int position) {
        String mov = mdata[position];
        holder.mtext.setText(mov);

    }

    @Override
    public int getItemCount() {
        if (null == mdata) return 0;

        return mdata.length;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mtext;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            mtext=(TextView) itemView.findViewById(R.id.review);

        }
    }
}

