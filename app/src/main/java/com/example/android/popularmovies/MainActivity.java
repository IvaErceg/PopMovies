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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {
    private static final String sortByPop = "popular";
    private static final String sortByRatings = "top_rated";
    private static final String sortByRelease = "now_playing";
    private ProgressBar mLoadingIndicator;
    MovieAdapter mAdapter;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rw_posters);
        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // specify an adapter (see also next example)
        mAdapter = new MovieAdapter(new ArrayList<Movie>(), this);
        mRecyclerView.setAdapter(mAdapter);
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
    public void onItemClick(int clickedItemIndex) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(i);
    }

    class MovieTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String sort = strings[0];
            return NetworkUtils.fetchMovieData(sort);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null && !movies.isEmpty()) {
                mAdapter.setMovieData(movies);
            }
        }

    }
}