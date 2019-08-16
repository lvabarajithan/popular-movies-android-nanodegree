package com.popularmovies.arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.popularmovies.db.MovieDatabase;
import com.popularmovies.db.dao.MovieDao;
import com.popularmovies.model.Movie;
import com.popularmovies.util.AppExecutors;

/**
 * Created by Abarajithan
 */
public class MovieDetailViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;
    private MovieDao movieDao;

    public MovieDetailViewModel(MovieDatabase db, long movieId) {
        this.movieDao = db.movieDao();
        this.movieLiveData = movieDao.getMovie(movieId);
    }

    public LiveData<Movie> getFavoriteLiveData() {
        return movieLiveData;
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
