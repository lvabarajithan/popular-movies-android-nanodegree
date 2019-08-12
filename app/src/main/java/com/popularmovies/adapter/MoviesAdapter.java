package com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.popularmovies.R;
import com.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private Context context;

    private List<Movie> movieList;
    private OnClickListener listener;

    public MoviesAdapter(Context context, OnClickListener listener) {
        this.context = context;
        this.movieList = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<Movie> movies) {
        this.movieList = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_movie_list, viewGroup, false);
        return new MovieHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
        Movie movie = movieList.get(position);
        movieHolder.titleTv.setText(movie.getTitle());
        Glide.with(context)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_cloud_off)
                .into(movieHolder.moviePosterView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        AppCompatImageView moviePosterView;
        AppCompatTextView titleTv;

        MovieHolder(@NonNull View itemView, final OnClickListener listener) {
            super(itemView);
            moviePosterView = itemView.findViewById(R.id.item_main_movie_list_image);
            titleTv = itemView.findViewById(R.id.item_main_movie_list_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(movieList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnClickListener {
        public void onClick(Movie movie);
    }

}
