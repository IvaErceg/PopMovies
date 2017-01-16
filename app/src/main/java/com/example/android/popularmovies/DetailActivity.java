package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView mTitle;
    TextView mVotes;
    TextView mSynopsis;
    TextView mRelease;
    ImageView mPoster;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = (TextView) findViewById(R.id.tw_title);
        mVotes = (TextView) findViewById(R.id.tw_vote);
        mSynopsis = (TextView) findViewById(R.id.tw_synopsis);
        mRelease = (TextView) findViewById(R.id.tw_release);
        mPoster = (ImageView) findViewById(R.id.iw_poster);

        //get data from intent and set it on appropriate view
        Intent i = getIntent();
        if (i.hasExtra(getString(R.string.title))) {
            String title = i.getStringExtra(getString(R.string.title));
            mTitle.setText(title);
        }
        if (i.hasExtra(getString(R.string.votes))) {
            String votes = i.getStringExtra(getString(R.string.votes));
            mVotes.setText(votes);
        }
        if (i.hasExtra(getString(R.string.synopsis))) {
            String synopsis = i.getStringExtra(getString(R.string.synopsis));
            mSynopsis.setText(synopsis);
        }
        if (i.hasExtra(getString(R.string.release))) {
            String release = i.getStringExtra(getString(R.string.release));
            mRelease.setText(release);
        }
        if (i.hasExtra(getString(R.string.poster))) {
            String poster = i.getStringExtra(getString(R.string.poster));
            Picasso.with(context).load(poster).into(mPoster);
        }
    }
}

