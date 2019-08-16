package com.popularmovies.arch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.popularmovies.db.MovieDatabase;

/**
 * Created by Abarajithan
 */
public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieDatabase db;
    private final long movieId;

    public MovieDetailViewModelFactory(MovieDatabase db, long movieId) {
        this.db = db;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(db, movieId);
    }

}
