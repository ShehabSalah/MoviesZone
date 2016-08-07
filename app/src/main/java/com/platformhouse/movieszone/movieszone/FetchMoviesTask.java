package com.platformhouse.movieszone.movieszone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shehab Salah on 7/27/16.
 */
//This class request the Movies List in Json and formatted to ArrayList of MovieHolder Objects
public abstract class FetchMoviesTask extends AsyncTask<String,Integer,ArrayList<MovieHolder>>{
    final String API_KEY = [Your-API-Key];
    final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    //Method getMoviesList responsible on getting the Json List of (Movies, videos and reviews)
    //according to Variable request
    public ArrayList<MovieHolder> getMoviesList(String request){
        //The next three Variables need to declare outside the try/catch to use them again after
        //the try/catch
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesListJsonStr = null;
        try{
            //Base URL
            final String MOIVES_LIST_BASE_URL = "http://api.themoviedb.org/3/movie/";
            //API Name
            final String API_KEY_PARAM = "api_key";
            Uri buildUri = Uri.parse(MOIVES_LIST_BASE_URL+request+"?").buildUpon()
                    .appendQueryParameter(API_KEY_PARAM,API_KEY)
                    .build();
            //Convert the uri to url
            URL url = new URL(buildUri.toString());
            // Create the request to TheMovieDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                //append each line from input stream into String Buffer
                buffer.append(line + "\n");
            }
            //if nothing in buffer
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            //Convert the StringBuffer to String
            moviesListJsonStr = buffer.toString();
            return JSONParser(moviesListJsonStr);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }catch(JSONException s){
            s.printStackTrace();
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    //This method convert the JSONString to ArrayList of array of Strings
    //each ArrayList item contain one movie data
    //each item in String array in the ArrayList contain single information about the movie like:
    //poster URL, Description
    public ArrayList<MovieHolder> JSONParser(String jsonStr) throws JSONException{
        // These are the names of the JSON objects that need to be extracted.
        final String MOVIES_LIST = "results";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_RELEASE_DATE = "release_date";
        //ArrayList to hold only the informations that we need to display them on the UI
        ArrayList<MovieHolder> arrayList = new ArrayList<>();
        //Extract the JSON Object and convert it to ArrayList of Array Strings
        JSONObject moviesJson = new JSONObject(jsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MOVIES_LIST);
        for(int i = 0; i < moviesArray.length(); i++){
            JSONObject movieItem = moviesArray.getJSONObject(i);
            arrayList.add(new MovieHolder(
                    movieItem.getInt(MOVIE_ID)
                    ,movieItem.getString(MOVIE_TITLE)
                    ,movieItem.getString(MOVIE_POSTER_PATH)
                    ,movieItem.getString(MOVIE_OVERVIEW)
                    ,movieItem.getString(MOVIE_VOTE_AVERAGE)
                    ,movieItem.getString(MOVIE_RELEASE_DATE)
                    ));
        }
        return arrayList;
    }

    @Override
    protected ArrayList<MovieHolder> doInBackground(String... params) {
        return getMoviesList(params[0]);
    }
    //Abstract methods to be implemented on all other classes
    @Override
    protected abstract void onPostExecute(ArrayList<MovieHolder> movieList);
    @Override
    protected abstract void onPreExecute();
    @Override
    protected abstract void onProgressUpdate(Integer... values);
    //The Following Code is by Google to check on network Connection State
    public boolean checkConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //if user connecting to the internet return true
            return true;
        } else {
            //if user not connecting to the internet return false
            return false;
        }
    }

}


/*
// ****** ( FetchMoviesTask Start ) ******
private class FetchMoviesTask extends AsyncTask<String,Integer,ArrayList<MovieHolder>> {

    @Override
    protected void onPreExecute() {
        Movies_list_progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected ArrayList<MovieHolder> doInBackground(String... params) {
        return new ().get(params[0]);
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(Movies_list_progressBar != null)
            Movies_list_progressBar.setProgress(values[0]);
    }

    @Override
    protected abstract void onPostExecute(ArrayList<MovieHolder> movieList){
        Movies_list_progressBar.setVisibility(View.INVISIBLE);
        UpdateAdapter(movieList);
    }
}// ****** ( FetchMoviesTask Start ) *******/
