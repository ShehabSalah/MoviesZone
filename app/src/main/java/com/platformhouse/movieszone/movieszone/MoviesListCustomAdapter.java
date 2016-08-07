package com.platformhouse.movieszone.movieszone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shehab Salah on 7/28/16.
 */
public class MoviesListCustomAdapter extends BaseAdapter {
    private ArrayList<MovieHolder> moviesList;
    private Context activity;
    private LayoutInflater inflater = null;
    private final String imageDir = "http://image.tmdb.org/t/p/w342";
    PlaceHolder placeHolder;

    public MoviesListCustomAdapter(Context context, ArrayList<MovieHolder> moviesList) {
        activity = context;
        this.moviesList = moviesList;

    }

    @Override
    public int getCount() {
        return moviesList.size() == 0 ? 0 : moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.movies_list_item, parent, false);
            placeHolder = new PlaceHolder();
            placeHolder.moviePoster = (ImageView) itemView.findViewById(R.id.movie_item_poster_MainActivity);
            itemView.setTag(placeHolder);
        } else {
            placeHolder = (PlaceHolder) itemView.getTag();
        }
        Picasso.with(activity)
                .load(imageDir + moviesList.get(position).getPoster())
                .placeholder(R.mipmap.placeholder_background)
                .into(placeHolder.moviePoster);


        return itemView;
    }

    private class PlaceHolder {
        ImageView moviePoster;
    }
}