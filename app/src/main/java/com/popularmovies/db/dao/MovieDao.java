package com.popularmovies.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Abarajithan
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    public List<Movie> getAll();

    @Insert
    public long insertMovie(Movie movie);

    @Delete
    public void deleteMovie(Movie movie);

    @Query("SELECT * FROM movies WHERE id=:movieId")
    public LiveData<Movie> getMovie(long movieId);

}

