package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movies.utilities.Json_movie;

/**
 * Created by ROHAN on 20-08-2017.
 */

public  class Trailer_Adapter extends RecyclerView.Adapter<Trailer_Adapter.AdapterViewHolder> {
    private String[] mdata;
    public static  int a;

    Json_movie type_key= new Json_movie();

    private Context mContext;
    private Cursor mcursor;
    public Trailer_Adapter(Context context) {
        this.mContext = context;



    }

    @Override
    public Trailer_Adapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list;
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
    public void onBindViewHolder(Trailer_Adapter.AdapterViewHolder holder, int position) {
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
            mtext=(TextView) itemView.findViewById(R.id.trailer_list);
            mtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mContext instanceof Trailer_Selector)
                    {

                        a=getAdapterPosition();

                        String d=type_key.key(a);
                        String url="https://www.youtube.com/watch?v="+d;
                        Uri webpage = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                            mContext.startActivity(intent);
                        }
                    }
                }
            });

        }
    }
}



