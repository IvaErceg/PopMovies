package com.example.android.popularmovies;

/**
 * Created by Iva on 12.1.2017..
 */

public class Movie{
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500/";
    private String mTitle;
    private String mPoster;

    public Movie(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String posterPath){
        mPoster =  BASE_POSTER_URL + posterPath;
    }
}

