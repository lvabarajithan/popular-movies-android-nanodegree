package com.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularmovies.adapter.ReviewsAdapter;
import com.popularmovies.arch.ReviewsViewModel;
import com.popularmovies.model.Review;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class ReviewsActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE_ID = "movie_id";

    public static void start(Context context, long movieId) {
        Intent starter = new Intent(context, ReviewsActivity.class);
        starter.putExtra(EXTRA_MOVIE_ID, movieId);
        context.startActivity(starter);
    }

    private ReviewsAdapter adapter;
    private ReviewsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Toolbar toolbar = findViewById(R.id.reviews_toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.reviews_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long movieId = intent.getLongExtra(EXTRA_MOVIE_ID, -1);
        if (movieId == -1) {
            Toast.makeText(this, "Cannot read review for this movie", Toast.LENGTH_SHORT).show();
            finish();
        }

        viewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);

        RecyclerView reviewsList = findViewById(R.id.reviews_list);
        reviewsList.setLayoutManager(new LinearLayoutManager(this));
        reviewsList.setHasFixedSize(true);
        reviewsList.setItemAnimator(new DefaultItemAnimator());

        adapter = new ReviewsAdapter();
        reviewsList.setAdapter(adapter);

        subscribeToReviews();
        viewModel.fetchReviews(movieId);

    }

    private void subscribeToReviews() {
        viewModel.getReviewsLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if (reviews == null) {
                    Toast.makeText(ReviewsActivity.this, "Cannot load reviews", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setData(reviews);
                    if (reviews.isEmpty()) {
                        Toast.makeText(ReviewsActivity.this, "There are no reviews yet", Toast.LENGTH_SHORT).show();
                    }
                }
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

}
