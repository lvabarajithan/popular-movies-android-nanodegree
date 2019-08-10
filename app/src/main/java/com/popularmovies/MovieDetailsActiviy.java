package com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // TODO:

    }

}
