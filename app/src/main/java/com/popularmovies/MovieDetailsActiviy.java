package com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.popularmovies.adapter.TrailersAdapter;
import com.popularmovies.api.ApiResult;
import com.popularmovies.model.Movie;
import com.popularmovies.model.Trailer;
import com.popularmovies.util.AppExecutors;
import com.popularmovies.util.InternetCheck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private AppCompatTextView releaseDateTv;
    private AppCompatTextView ratingTv;
    private AppCompatTextView descriptionTv;
    private AppCompatImageView headerIv;
    private AppCompatImageView posterIv;

    private FloatingActionButton favFab;

    private TrailersAdapter trailersAdapter;

    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.details_movies_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie == null) {
            Toast.makeText(this, "Cannot load movie", Toast.LENGTH_SHORT).show();
            finish();
        }

        releaseDateTv = findViewById(R.id.details_movie_release_date);
        ratingTv = findViewById(R.id.details_movie_ratings);
        descriptionTv = findViewById(R.id.details_movie_description);
        headerIv = findViewById(R.id.details_movie_header);
        posterIv = findViewById(R.id.details_movie_poster);
        favFab = findViewById(R.id.details_movie_favourite);
        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add to favourite
            }
        });

        RecyclerView trailersList = findViewById(R.id.details_movie_trailers_list);
        trailersList.setItemAnimator(new DefaultItemAnimator());
        trailersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailersList.setHasFixedSize(true);

        trailersAdapter = new TrailersAdapter(this);
        trailersList.setAdapter(trailersAdapter);

        populateUI(movie);
        setTitle(movie.getTitle());

        populateTrailers();

    }

    private void populateTrailers() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        new InternetCheck(manager, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isOnline) {
                if (isOnline) {
                    fetchTrailers();
                } else {
                    Toast.makeText(MovieDetailsActiviy.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    private void fetchTrailers() {
        AppExecutors.get().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MoviesApp.getMoviesService().getTrailersFor(movie.getId()).enqueue(new Callback<ApiResult<Trailer>>() {
                    @Override
                    public void onResponse(Call<ApiResult<Trailer>> call, Response<ApiResult<Trailer>> response) {
                        final ApiResult<Trailer> apiResult = response.body();
                        if (apiResult != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    trailersAdapter.setData(apiResult.getResults());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResult<Trailer>> call, Throwable t) {
                        Toast.makeText(MovieDetailsActiviy.this, "Cannot load trailers", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void populateUI(Movie movie) {
        releaseDateTv.setText(movie.getReleaseDate());
        ratingTv.setText(getString(R.string.details_movie_rating, movie.getRating()));
        descriptionTv.setText(movie.getSynopsis());

        Glide.with(this)
                .load(movie.getHeaderUrl())
                .into(headerIv);

        Glide.with(this)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_cloud_off)
                .into(posterIv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_details_share:
                // TODO: Share trailer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
