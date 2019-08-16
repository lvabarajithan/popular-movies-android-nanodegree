package com.popularmovies.arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.popularmovies.MoviesApp;
import com.popularmovies.api.ApiResult;
import com.popularmovies.model.Movie;
import com.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abarajithan
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> moviesLiveData;

    public MainViewModel() {
        this.moviesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public void fetchMovies(final String endpoint) {
        AppExecutors.get().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MoviesApp.getMoviesService().listMoviesBy(endpoint).enqueue(new Callback<ApiResult<Movie>>() {
                    @Override
                    public void onResponse(Call<ApiResult<Movie>> call, Response<ApiResult<Movie>> response) {
                        final ApiResult<Movie> apiResult = response.body();
                        moviesLiveData.setValue(apiResult == null ? null : apiResult.getResults());
                    }

                    @Override
                    public void onFailure(Call<ApiResult<Movie>> call, Throwable t) {
                        moviesLiveData.setValue(null);
                    }
                });
            }
        });
    }

}
