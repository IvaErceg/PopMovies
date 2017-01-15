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

        Intent i = getIntent();
        if (i.hasExtra("title")) {
            String title = i.getStringExtra("title");
            mTitle.setText(title);
        }
        if (i.hasExtra("votes")) {
            String votes = i.getStringExtra("votes");
            mVotes.setText(votes);
        }
        if (i.hasExtra("synopsis")) {
            String synopsis = i.getStringExtra("synopsis");
            mSynopsis.setText(synopsis);
        }
        if (i.hasExtra("release")) {
            String release = i.getStringExtra("release");
            mRelease.setText(release);
        }
        if (i.hasExtra("poster")) {
            String poster = i.getStringExtra("poster");
            Picasso.with(context).load(poster).into(mPoster);
        }
    }
}

