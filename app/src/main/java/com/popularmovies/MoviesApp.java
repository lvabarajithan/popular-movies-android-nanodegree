package com.popularmovies;

import android.app.Application;

import com.popularmovies.api.MoviesService;
import com.popularmovies.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abarajithan
 */
public class MoviesApp extends Application {

    private static MoviesService moviesService = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_PREFIX)
                .build();
        moviesService = retrofit.create(MoviesService.class);
    }

    public static MoviesService getMoviesService() {
        return moviesService;
    }

}
