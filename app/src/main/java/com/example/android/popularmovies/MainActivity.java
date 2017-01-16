package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {
    //constants
    private static final String sortByPop = "popular";
    private static final String sortByRatings = "top_rated";
    private static final String sortByRelease = "now_playing";
    private static final int NUM_COLUMNS = 3;

    ProgressBar mLoadingIndicator;
    MovieAdapter mAdapter;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rw_posters);
        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // specify an adapter
        mAdapter = new MovieAdapter(new ArrayList<Movie>(), this);
        mRecyclerView.setAdapter(mAdapter);
        //start AsynTask with default sort
        new MovieTask().execute(sortByPop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortRating:
                //only one menu item can be checked
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                new MovieTask().execute(sortByRatings);
                return true;
            case R.id.sortPopularity:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                new MovieTask().execute(sortByPop);
                return true;
            case R.id.sortRelease:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                new MovieTask().execute(sortByRelease);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra(getString(R.string.title), movie.getTitle());
        i.putExtra(getString(R.string.synopsis), movie.getSynopsis());
        i.putExtra(getString(R.string.votes), movie.getVotes());
        i.putExtra(getString(R.string.release), movie.getReleaseDate());
        i.putExtra(getString(R.string.poster), movie.getPoster());
        startActivity(i);
    }

    //subclass of AsyncTask, responsible for networking task on the backround thread

    class MovieTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        // takes sorting parameter, fetch data from network and return list of movies

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String sort = strings[0];
            return NetworkUtils.fetchMovieData(sort);
        }
        // puts returned list of movies into an adapter
        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null && !movies.isEmpty()) {
                mAdapter.setMovieData(movies);
            }
        }

    }
}