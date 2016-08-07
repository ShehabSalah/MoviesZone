package com.platformhouse.movieszone.movieszone;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


public class MoviesActivity extends AppCompatActivity {
    //This Variable used to save last position of the grid view before the screen rotate
    static int lastPosition = GridView.INVALID_POSITION;
    //Order Constant Variables
    static String Order = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment()).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.refresh:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainFragment()).commit();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    //Fragment Class and methods        ****** ( Start ) ******
    public static class MainFragment extends Fragment{
        //Key to save the last position in SaveInstanceState
        private static final String POSITION_KEY = "position";
        //Key to save the last order in SaveInstanceState
        private static final String ORDER_KEY = "order";
        //global variables
        GridView moviesListGridView;
        ProgressBar Movies_list_progressBar;
        ArrayList<MovieHolder> moviesList = null;
        MoviesListCustomAdapter moviesListCustomAdapter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.order_menu, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            lastPosition = 0;
            if (id == R.id.popular) {
                Order = "popular";
            }
            if (id == R.id.top_rated) {
                Order = "top_rated";
            }
            updateMoviesList(Order);
            return true;
        }

        //Create view: used to inflate movies_list.xml in the movies activity which contain the movies items list
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //Inflate the movies_list view into movies Activity
            View mainView = inflater.inflate(R.layout.movies_list,container,false);
            //Connect the grideView variable with the GridView in the XML
            moviesListGridView = (GridView)mainView.findViewById(R.id.movies_list_items);
            //Connect the progressBar variable with the ProgressBar in the WML
            Movies_list_progressBar = (ProgressBar)getActivity().findViewById(R.id.Movies_list_progressBar);
            //Set the progressBar by default invisible
            Movies_list_progressBar.setVisibility(View.INVISIBLE);
            //Making the gridView clickable
            moviesListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(moviesList != null){
                        startActivity(DetailsActivity.getDetailsIntent(getActivity(), moviesList.get(position)));
                    }
                }
            });
            //Check whether we're recreating a previously destroyed application instance
            if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
                //If (YES) restore value of members from saved state
                lastPosition = savedInstanceState.getInt(POSITION_KEY);
                if (savedInstanceState.containsKey(ORDER_KEY)){
                    Order = savedInstanceState.getString(ORDER_KEY);
                }
            }
            updateMoviesList(Order);
            //return the view
            return mainView;
        }
        @Override
        public void onSaveInstanceState(Bundle outState) {
            //Save the user current state
            super.onSaveInstanceState(outState);
            //get the last GridView position
            lastPosition = moviesListGridView.getFirstVisiblePosition();
            //Save the last GridView position with the position key
            outState.putInt(POSITION_KEY, lastPosition);
            outState.putString(ORDER_KEY,Order);
        }
        //Method that used to update the movies list
        private void updateMoviesList(String order){
            FetchMoviesTask fetchMoviesTask = new FetchMoviesTask() {
                @Override
                protected void onPostExecute(ArrayList<MovieHolder> movieList) {
                    Movies_list_progressBar.setVisibility(View.INVISIBLE);
                    UpdateAdapter(movieList);
                }

                @Override
                protected void onPreExecute() {
                    Movies_list_progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    if(Movies_list_progressBar != null)
                        Movies_list_progressBar.setProgress(values[0]);
                }
            };
            //If there is internet connection
            if(fetchMoviesTask.checkConnection(getActivity().getApplicationContext())){
                getActivity().setTitle((order.equals("popular")?"Popular":"Top Rated") + " Movies");
                //Fire the background Thread to fetch movies data by the order
                fetchMoviesTask.execute(order);
            }else{
                //Display message Error
                Toast.makeText(getActivity().getApplicationContext(),"Notwork not available",Toast.LENGTH_LONG).show();
            }
        }
        //This method is responsible on update the UI front Grid list with the coming data from API
        private void UpdateAdapter(ArrayList<MovieHolder> moviesList){
            //If the connection done successfully and the movies list not equal null
            if(moviesList != null){
                //Update the global moviesList with the new movies list
                this.moviesList = moviesList;
                //Send movies list to the customAdpter
                moviesListCustomAdapter = new MoviesListCustomAdapter(getActivity(),moviesList);
                //Set the result Adapter in the the GridView Adapter
                moviesListGridView.setAdapter(moviesListCustomAdapter);
                //If we're recreating a previously destroyed application instance
                if (lastPosition != GridView.INVALID_POSITION) {
                    //Go to the position of the previous destroyed application + the different column number on the GridView
                    moviesListGridView.smoothScrollToPosition(lastPosition==0?lastPosition:lastPosition+4);
                }
                moviesListGridView.deferNotifyDataSetChanged();
            }else{ //If there is any kind of miss connection in the middle of retrieving the data
                //Display message Error
                Toast.makeText(getActivity().getApplicationContext(),"Notwork not available",Toast.LENGTH_LONG).show();
            }
        }
    }//Fragment Class and methods        ****** ( End ) ******
}