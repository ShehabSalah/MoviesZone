package com.platformhouse.movieszone.movieszone;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    static Intent getDetailsIntent(Context context, MovieHolder entity) {
        return new Intent(context, DetailsActivity.class).putExtra("moviesList", entity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Movie Details");
        getFragmentManager().beginTransaction()
                .replace(R.id.details_container, new PlasholderDetailsFragment()).commit();
    }

    public static class PlasholderDetailsFragment extends Fragment {
        final String imageDir = "http://image.tmdb.org/t/p/w342";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Inflate the movie_details view into details Activity
            View detailsView = inflater.inflate(R.layout.movie_details, container, false);
            MovieHolder movieHolder = null;
            Intent intent = getActivity().getIntent();
            // Get the extras (if there are any)
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (extras.containsKey("moviesList")) {
                    movieHolder = (MovieHolder) intent.getExtras().getSerializable("moviesList");
                }
            }
            if (movieHolder != null) {
                ImageView posterImage = (ImageView) detailsView.findViewById(R.id.movie_item_poster_DetailsActivity);
                TextView movieName = (TextView) detailsView.findViewById(R.id.movie_item_name_DetailsActivity);
                TextView movieDate = (TextView) detailsView.findViewById(R.id.movie_item_date_DetailsActivity);
                TextView movieRate = (TextView) detailsView.findViewById(R.id.movie_item_rate_DetailsActivity);
                TextView movieOverview = (TextView) detailsView.findViewById(R.id.movie_item_overview_DetailsActivity);
                Picasso.with(getActivity().getApplicationContext())
                        .load(imageDir + movieHolder.getPoster())
                        .placeholder(R.mipmap.placeholder_background)
                        .into(posterImage);
                movieName.setText(movieHolder.getTitle());
                movieDate.setText(movieHolder.getRelease_date());
                movieRate.setText(movieHolder.getRate() + "/10");
                movieOverview.setText(movieHolder.getOverview());

            } else {
                Toast.makeText(getActivity(), "There is no data to display!", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            return detailsView;
        }
    }
}