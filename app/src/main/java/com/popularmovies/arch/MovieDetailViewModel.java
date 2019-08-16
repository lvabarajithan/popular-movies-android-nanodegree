package com.popularmovies.arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.popularmovies.MoviesApp;
import com.popularmovies.api.ApiResult;
import com.popularmovies.db.MovieDatabase;
import com.popularmovies.db.dao.MovieDao;
import com.popularmovies.model.Movie;
import com.popularmovies.model.Review;
import com.popularmovies.model.Trailer;
import com.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abarajithan
 */
public class MovieDetailViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;
    private MutableLiveData<List<Trailer>> trailersLiveData;
    private MutableLiveData<List<Review>> reviewsLiveData;

    private MovieDao movieDao;

    private long movieId;

    public MovieDetailViewModel(MovieDatabase db, long movieId) {
        this.movieId = movieId;
        this.movieDao = db.movieDao();
        this.movieLiveData = movieDao.getMovie(movieId);
        this.trailersLiveData = new MutableLiveData<>();
        this.reviewsLiveData = new MutableLiveData<>();
    }

    public LiveData<Movie> getFavoriteLiveData() {
        return movieLiveData;
    }

    public LiveData<List<Trailer>> getTrailersLiveData() {
        return trailersLiveData;
    }

    public void fetchTrailers() {
        AppExecutors.get().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MoviesApp.getMoviesService().getTrailersFor(movieId).enqueue(new Callback<ApiResult<Trailer>>() {
                    @Override
                    public void onResponse(Call<ApiResult<Trailer>> call, Response<ApiResult<Trailer>> response) {
                        final ApiResult<Trailer> apiResult = response.body();
                        trailersLiveData.setValue(apiResult != null ? apiResult.getResults() : null);
                    }

                    @Override
                    public void onFailure(Call<ApiResult<Trailer>> call, Throwable t) {
                        trailersLiveData.setValue(null);
                    }
                });
            }
        });
    }

    public LiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public void fetchReviews() {
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

    public void addOrRemoveFav(boolean add, Movie movie) {
        if (add) {
            addToFav(movie);
        } else {
            removeFav(movie);
        }
    }

    private void removeFav(final Movie movie) {
        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDao.deleteMovie(movie);
            }
        });
    }

    private void addToFav(final Movie movie) {
        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDao.insertMovie(movie);
            }
        });
    }

}
