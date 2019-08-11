package com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.popularmovies.model.Movie;

/**
 * Created by Abarajithan
 */
public class MovieDetailsActiviy extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    public static void start(Context context, Movie movie) {
        Intent starter = new Intent(context, MovieDetailsActiviy.class);
        starter.putExtra(EXTRA_MOVIE, movie);
        context.startActivity(starter);
    }

    private AppCompatTextView titleTv;
    private AppCompatTextView releaseDateTv;
    private AppCompatTextView ratingTv;
    private AppCompatTextView descriptionTv;
    private AppCompatImageView posterIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie == null) {
            Toast.makeText(this, "Cannot load movie", Toast.LENGTH_SHORT).show();
            finish();
        }

        titleTv = findViewById(R.id.details_movie_title);
        releaseDateTv = findViewById(R.id.details_movie_release_date);
        ratingTv = findViewById(R.id.details_movie_ratings);
        descriptionTv = findViewById(R.id.details_movie_description);
        posterIv = findViewById(R.id.details_movie_poster);

        populateUI(movie);
        setTitle(R.string.details_movie_toolbar_title);

    }

    private void populateUI(Movie movie) {
        titleTv.setText(movie.getTitle());
        releaseDateTv.setText(movie.getReleaseDate());
        ratingTv.setText(getString(R.string.details_movie_rating, movie.getRating()));
        descriptionTv.setText(movie.getSynopsis());

        Glide.with(this)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_cloud_off)
                .into(posterIv);
    }

}
