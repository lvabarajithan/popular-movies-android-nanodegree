package com.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.popularmovies.adapter.MoviesAdapter;
import com.popularmovies.adapter.OnClickListener;
import com.popularmovies.arch.FavouriteViewModel;
import com.popularmovies.model.Movie;
import com.popularmovies.util.Utils;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class FavouritesActivity extends AppCompatActivity implements OnClickListener<Movie> {

    private FavouriteViewModel viewModel;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.reviews_toolbar);
        toolbar.setTitle(R.string.favorites_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

        RecyclerView favList = findViewById(R.id.main_movies_list);
        favList.setHasFixedSize(true);
        favList.setLayoutManager(new GridLayoutManager(this, Utils.getSpanCount(this)));
        favList.setItemAnimator(new DefaultItemAnimator());

        adapter = new MoviesAdapter(this, this);
        favList.setAdapter(adapter);

        subscribeFavorites();

    }

    private void subscribeFavorites() {
        viewModel.getMoviesLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.setData(movies);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {
        MovieDetailsActivity.start(this, movie);
    }

}
