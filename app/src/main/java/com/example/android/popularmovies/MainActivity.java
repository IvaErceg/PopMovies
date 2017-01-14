package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String sortByPop = "popular";
    private static final String sortByRatings = "top_rated";
    private static final String sortByRelease = "now_playing";
    private ProgressBar mLoadingIndicator;
    GridView mPostersGrid;
    MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mPostersGrid = (GridView) findViewById(R.id.gw_posters);
        new MovieTask().execute(sortByPop);

        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        mPostersGrid.setAdapter(mAdapter);

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
            mAdapter.clear();
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null && !movies.isEmpty()) {
                mAdapter.addAll(movies);
            }

        }


    }
}