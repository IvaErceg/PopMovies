package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Iva on 13.1.2017..
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> movies;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        public ViewHolder(View v) {
            super(v);
            poster = (ImageView) v.findViewById(R.id.iw_poster);
        }
    }
    public MovieAdapter(List<Movie> data, Context context) {
        movies = data;
        mContext = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);
        Context context = holder.poster.getContext();
        Picasso.with(context).load(currentMovie.getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovieData(List<Movie> movieData) {
        movies = movieData;
        notifyDataSetChanged();
    }
}