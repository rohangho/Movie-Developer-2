package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.utilities.Json_builder;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

/**
 * Created by ROHAN on 29-07-2017.
 */

public class adapter extends RecyclerView.Adapter<adapter.AdapterViewHolder> {

    private String[] mdata;
    private final AdapterOnClickHandler mhandler;
    private String[] mimaeg;
    private Context context;
    public adapter(AdapterOnClickHandler mhandler, Context context){
        this.mhandler = mhandler;
        this.context = context;
    }

    public interface AdapterOnClickHandler {
        void onClick(String movies);
    }
    public adapter(AdapterOnClickHandler clickHandler) {
        mhandler = clickHandler;
    }


    public class  AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mTextView;

        public final ImageView mimg;


        public AdapterViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_display_data);
            mimg=(ImageView)view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieofDay = mdata[adapterPosition];
            mhandler.onClick(movieofDay);
        }
    }

    @Override
    public void onBindViewHolder(adapter.AdapterViewHolder holder, int position) {

        String mov = mdata[position];
        String imahe=mimaeg[position];
        Log.v(TAG, "index=" + imahe);
        Picasso.with(context).load(imahe).into(holder.mimg);
        holder.mTextView.setText(mov);


    }


    @Override
    public adapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }



    @Override
    public int getItemCount() {

        if (null == mdata) return 0;
        return mdata.length;
    }
    Json_builder obj=new Json_builder();
    public void setData(String[] Data) {
        mdata = Data;
        mimaeg=obj.CHECKiMAGE();
        notifyDataSetChanged();
    }


}






