package com.popularmovies;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularmovies.adapter.MoviesAdapter;
import com.popularmovies.model.Movie;
import com.popularmovies.util.Constants;
import com.popularmovies.util.InternetCheck;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnClickListener, OnLoadListener<Movie> {

    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView moviesLv = findViewById(R.id.main_movies_list);
        moviesLv.setHasFixedSize(true);
        moviesLv.setLayoutManager(new GridLayoutManager(this, 2));
        moviesLv.setItemAnimator(new DefaultItemAnimator());

        adapter = new MoviesAdapter(this, this);
        moviesLv.setAdapter(adapter);

        populateMovies(Constants.ENDPOINT_POPULAR);

    }

    private void populateMovies(final String endpointPopular) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        new InternetCheck(manager, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isOnline) {
                if (isOnline) {
                    new MovieListTask(MainActivity.this).execute(endpointPopular);
                } else {
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    @Override
    public void onClick(Movie movie) {
        if (movie != null) {
            MovieDetailsActiviy.start(this, movie);
        }
    }

    @Override
    public void onLoad(List<Movie> list) {
        if (list != null) {
            adapter.setData(list);
        } else {
            Toast.makeText(this, "Cannot load movies", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                populateMovies(Constants.ENDPOINT_POPULAR);
                item.setChecked(true);
                return true;
            case R.id.action_top_rated_movies:
                populateMovies(Constants.ENDPOINT_TOP_RATED);
                item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
