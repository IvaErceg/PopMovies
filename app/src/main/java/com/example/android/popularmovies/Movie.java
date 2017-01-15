package com.example.android.popularmovies;

/**
 * Created by Iva on 12.1.2017..
 */

public class Movie {
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500/";
    private String mTitle;
    private String mPoster;
    private String mSynopsis;
    private String mReleaseDate;
    private String mVotes;

    public Movie(String title, String synopsis, String release, String votes) {
        mTitle = title;
        mSynopsis = synopsis;
        mReleaseDate = release;
        mVotes = votes;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getVotes() {
        return mVotes;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setPoster(String posterPath) {
        mPoster = BASE_POSTER_URL + posterPath;
    }
}

