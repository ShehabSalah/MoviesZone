package com.platformhouse.movieszone.movieszone;

import java.io.Serializable;

/**
 * Created by Shehab Salah on 7/31/16.
 */
public class MovieHolder implements Serializable{
    private int id;
    private String title;
    private String poster;
    private String overview;
    private String rate;
    private String release_date;
    public MovieHolder(int id,String title,String poster,String overview,String rate,String release_date){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rate = rate;
        this.release_date = release_date;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getRate() {
        return rate;
    }

    public String getRelease_date() {
        return release_date;
    }
}