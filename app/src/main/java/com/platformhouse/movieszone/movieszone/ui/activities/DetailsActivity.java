package com.platformhouse.movieszone.movieszone.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.ui.fragments.DetailsFragment;
import com.platformhouse.movieszone.movieszone.util.Constants;

/*
 * Created by Shehab Salah on 8/19/16.
 */
public class DetailsActivity extends AppCompatActivity {

    public static Intent getDetailsIntent(Context context, MovieColumnHolder entity) {
        return new Intent(context, DetailsActivity.class).putExtra("moviesList", entity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Movie Details");
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putSerializable("moviesList", getIntent().getSerializableExtra("moviesList"));
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment).commit();
        }

    }
}
