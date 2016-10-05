package com.platformhouse.movieszone.movieszone.data.movie;

import java.io.Serializable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on holding the movie data
 */
public class MovieColumnHolder implements Serializable {
    private String poster_path;
    private String overview;
    private String release_date;
    private int id;
    private int _id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private float popularity;
    private int vote_count;
    private float vote_average;
    private int favorite;

    public MovieColumnHolder(String poster_path, String overview, String release_date, int id,
                             String original_title, String original_language, String title,
                             String backdrop_path, float popularity, int vote_count, float vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.favorite =  0;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getFavorite() {
        return favorite;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
