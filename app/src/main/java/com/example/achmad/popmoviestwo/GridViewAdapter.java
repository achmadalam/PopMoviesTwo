package com.example.achmad.popmoviestwo;

/**
 * Created by Achmad on 06/05/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.achmad.popmoviestwo.Data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Movie> mGridData = new ArrayList<Movie>();
    private LayoutInflater mInflater;

    public GridViewAdapter(Context mContext, ArrayList<Movie> mGridData) {
        this.mContext = mContext;
        this.mGridData = mGridData;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mGridData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGridData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            row = mInflater.inflate(R.layout.content_main, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        if (position == mGridData.size()) {
            MovieFragment mv = new MovieFragment();
            mv.fetchMovie(2);
        }
        Movie item = mGridData.get(position);
        holder.titleTextView.setText(item.getTitle());
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+item.getPosterPath()).into(holder.imageView);

        return row;
    }


    static class ViewHolder {
        @Bind(R.id.grid_item_image)
        ImageView imageView;
        @Bind(R.id.grid_item_title)
        TextView titleTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
