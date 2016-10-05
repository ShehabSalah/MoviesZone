package com.platformhouse.movieszone.movieszone.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumnHolder;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumns;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumnHolder;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumns;
import com.platformhouse.movieszone.movieszone.ui.adapters.ReviewListAdapter;
import com.platformhouse.movieszone.movieszone.ui.adapters.TrailerListAdapter;
import com.platformhouse.movieszone.movieszone.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shehab Salah on 8/20/16.
 *
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView trialerList;
    ListView reviewList;
    TrailerListAdapter trailerListAdapter;
    ReviewListAdapter reviewListAdapter;
    int detail_id = 0;
    TextView favorite_text;
    MovieColumnHolder movieColumnHolder;
    TrailerColumnHolder trailer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the movie_details view into details Activity

        View detailsView = inflater.inflate(R.layout.movie_details, container, false);
        ScrollView detail_scroll = (ScrollView) detailsView.findViewById(R.id.detail_scroll);
        movieColumnHolder = null;
        //Intent intent = getActivity().getIntent();
        // Get the extras (if there are any)
        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey("moviesList")) {
                movieColumnHolder = (MovieColumnHolder) extras.getSerializable("moviesList");
            }
        }
        if (movieColumnHolder != null) {
            ImageView posterImage = (ImageView) detailsView.findViewById(R.id.movie_item_poster_DetailsActivity);
            TextView movieName = (TextView) detailsView.findViewById(R.id.movie_item_name_DetailsActivity);
            TextView movieDate = (TextView) detailsView.findViewById(R.id.movie_item_date_DetailsActivity);
            TextView movieRate = (TextView) detailsView.findViewById(R.id.movie_item_rate_DetailsActivity);
            TextView movieOverview = (TextView) detailsView.findViewById(R.id.movie_item_overview_DetailsActivity);
            favorite_text = (TextView) detailsView.findViewById(R.id.favorite_text);
            LinearLayout f_button = (LinearLayout) detailsView.findViewById(R.id.favorite_button);
            trialerList = (ListView) detailsView.findViewById(R.id.trailer_listview);
            reviewList = (ListView) detailsView.findViewById(R.id.review_listview);
            Picasso.with(getActivity().getApplicationContext())
                    .load(Constants.IMAGE_URL + movieColumnHolder.getPoster_path())
                    .placeholder(R.mipmap.placeholder_background)
                    .into(posterImage);
            movieName.setText(movieColumnHolder.getOriginal_title());
            movieDate.setText(movieColumnHolder.getRelease_date());
            movieRate.setText(getString(R.string.vote,Float.toString(movieColumnHolder.getVote_average())));
            movieOverview.setText(movieColumnHolder.getOverview());
            detail_id = movieColumnHolder.getId();
            if(movieColumnHolder.getFavorite() == 0){
                favorite_text.setText(R.string.favorite);
            }else{
                favorite_text.setText(R.string.unfavorite);
            }
            f_button.setOnClickListener(new LinearLayout.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(movieColumnHolder.getFavorite() == 0){
                        favorite_text.setText(R.string.unfavorite);
                        ContentValues values = new ContentValues();
                        values.put(MovieColumns.COL_FAVORITE,Constants.favorite);
                        movieColumnHolder.setFavorite(Constants.favorite);
                        getActivity().getContentResolver().update(MovieColumns.CONTENT_URI,
                                values,MovieColumns.COL_MOVIEDB_ID + " = ? ",new String[]{Integer.toString(detail_id)});
                    }else{
                        favorite_text.setText(R.string.favorite);
                        ContentValues values = new ContentValues();
                        values.put(MovieColumns.COL_FAVORITE,Constants.unFavorite);
                        movieColumnHolder.setFavorite(Constants.unFavorite);
                        getActivity().getContentResolver().update(MovieColumns.CONTENT_URI,
                                values,MovieColumns.COL_MOVIEDB_ID + " = ? ",new String[]{Integer.toString(detail_id)});
                    }
                }
            });
            trialerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_LINK+trailer.getKey())));

                }
            });
            detail_scroll.smoothScrollTo(0,0);
            getLoaderManager().initLoader(Constants.TRAILER_LOADER, null, this);
            getLoaderManager().initLoader(Constants.REVIEW_LOADER, null, this);

        }
        return detailsView;
    }

    private void updateTrailerAdapter(ArrayList<Object> trailerArrayList) {
        //If the connection done successfully and the movies list not equal null
        if (trailerArrayList != null) {
            //Send list to the customAdpter
                trailerListAdapter = new TrailerListAdapter(getActivity(), trailerArrayList);
                trialerList.setAdapter(trailerListAdapter);
        }
    }
    private void updateReviewAdapter(ArrayList<Object> reviewArrayList){
        if (reviewArrayList != null){
            reviewListAdapter = new ReviewListAdapter(getActivity(), reviewArrayList);
            reviewList.setAdapter(reviewListAdapter);
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case Constants.TRAILER_LOADER:
                return new CursorLoader(getActivity(),
                        TrailerColumns.CONTENT_URI,
                        null,
                        TrailerColumns.COL_MOVIEDB_ID + " = ? ",
                        new String[]{Integer.toString(detail_id)},
                        null);
            case Constants.REVIEW_LOADER:
                return new CursorLoader(getActivity(),
                        ReviewColumns.CONTENT_URI,
                        null,
                        ReviewColumns.COL_MOVIEDB_ID + " = ? ",
                        new String[]{Integer.toString(detail_id)},
                        null);
        }
        return  null;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(detail_id > 0){
            ArrayList<Object> dbList = new ArrayList<>();
            if(cursor != null && cursor.moveToFirst()){
                switch (loader.getId()){
                    case Constants.TRAILER_LOADER:
                        do{
                            trailer = new TrailerColumnHolder(
                                    cursor.getString(cursor.getColumnIndex(TrailerColumns.COL_KEY)),
                                    cursor.getString(cursor.getColumnIndex(TrailerColumns.COL_NAME)),
                                    cursor.getString(cursor.getColumnIndex(TrailerColumns.COL_SIZE)),
                                    cursor.getString(cursor.getColumnIndex(TrailerColumns.COL_TYPE))
                            );
                            dbList.add(trailer);
                        }while (cursor.moveToNext());
                        updateTrailerAdapter(dbList);
                        break;
                    case Constants.REVIEW_LOADER:
                        do{
                            ReviewColumnHolder review = new ReviewColumnHolder(
                                    cursor.getString(cursor.getColumnIndex(ReviewColumns.COL_AUTHOR)),
                                    cursor.getString(cursor.getColumnIndex(ReviewColumns.COL_CONTENT)),
                                    cursor.getString(cursor.getColumnIndex(ReviewColumns.COL_URL))
                            );
                            dbList.add(review);
                        }while (cursor.moveToNext());
                        updateReviewAdapter(dbList);
                        break;
                }
            }

        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}