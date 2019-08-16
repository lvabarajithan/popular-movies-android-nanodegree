package com.popularmovies.arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.popularmovies.MoviesApp;
import com.popularmovies.api.ApiResult;
import com.popularmovies.model.Review;
import com.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abarajithan
 */
public class ReviewsViewModel extends ViewModel {

    private MutableLiveData<List<Review>> reviewsLiveData;

    public ReviewsViewModel() {
        this.reviewsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public void fetchReviews(final long movieId) {
        AppExecutors.get().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MoviesApp.getMoviesService().getReviewsFor(movieId).enqueue(new Callback<ApiResult<Review>>() {
                    @Override
                    public void onResponse(Call<ApiResult<Review>> call, Response<ApiResult<Review>> response) {
                        ApiResult<Review> apiResult = response.body();
                        reviewsLiveData.setValue(apiResult == null ? null : apiResult.getResults());
                    }

                    @Override
                    public void onFailure(Call<ApiResult<Review>> call, Throwable t) {
                        reviewsLiveData.setValue(null);
                    }
                });
            }
        });
    }

}
