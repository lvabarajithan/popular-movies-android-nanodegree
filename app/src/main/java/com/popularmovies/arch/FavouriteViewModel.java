package com.popularmovies.arch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.popularmovies.db.MovieDatabase;
import com.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class FavouriteViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> moviesLiveData;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase db = MovieDatabase.get(application);
        moviesLiveData = db.movieDao().getAll();
    }

    public LiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

}
