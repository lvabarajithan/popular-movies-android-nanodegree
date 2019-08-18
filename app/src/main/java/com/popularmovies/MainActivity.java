package com.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.popularmovies.adapter.MoviesAdapter;
import com.popularmovies.adapter.OnClickListener;
import com.popularmovies.arch.MainViewModel;
import com.popularmovies.model.Movie;
import com.popularmovies.util.Constants;
import com.popularmovies.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener<Movie> {

    private static final String EXTRA_MOVIES = "movies_list";
    private static final String EXTRA_MOVIE_FILTER = "movie_filter";

    private MoviesAdapter adapter;

    private MainViewModel viewModel;

    private int checkedItemId = R.id.action_popular_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.reviews_toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        RecyclerView moviesLv = findViewById(R.id.main_movies_list);
        moviesLv.setHasFixedSize(true);
        moviesLv.setLayoutManager(new GridLayoutManager(this, Utils.getSpanCount(this)));
        moviesLv.setItemAnimator(new DefaultItemAnimator());

        adapter = new MoviesAdapter(this, this);
        moviesLv.setAdapter(adapter);

        if (savedInstanceState != null) {
            adapter.setData(savedInstanceState.<Movie>getParcelableArrayList(EXTRA_MOVIES));
            this.checkedItemId = savedInstanceState.getInt(EXTRA_MOVIE_FILTER);
        } else {
            viewModel.setSource(Constants.ENDPOINT_POPULAR);
        }
        subscribeToMovies();

    }

    private void subscribeToMovies() {
        viewModel.getMoviesLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if (movieList != null) {
                    adapter.setData(movieList);
                } else {
                    Toast.makeText(MainActivity.this, "Cannot load movies", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View root, Movie movie) {
        if (movie != null) {
            MovieDetailsActivity.start(this, movie, root.findViewById(R.id.item_main_movie_list_image));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(checkedItemId).setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                viewModel.setSource(Constants.ENDPOINT_POPULAR);
                setChecked(item);
                return true;
            case R.id.action_top_rated_movies:
                viewModel.setSource(Constants.ENDPOINT_TOP_RATED);
                setChecked(item);
                return true;
            case R.id.action_favorites:
                viewModel.setSource(null);
                setChecked(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setChecked(MenuItem item) {
        item.setChecked(true);
        this.checkedItemId = item.getItemId();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_MOVIES, new ArrayList<Parcelable>(adapter.getData()));
        outState.putInt(EXTRA_MOVIE_FILTER, checkedItemId);
    }
}
