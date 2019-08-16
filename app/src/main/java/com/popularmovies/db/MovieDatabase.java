package com.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.popularmovies.db.dao.MovieDao;
import com.popularmovies.model.Movie;

/**
 * Created by Abarajithan
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movies_db";

    private static MovieDatabase INSTANCE = null;

    public static MovieDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, MovieDatabase.class, DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();

}
