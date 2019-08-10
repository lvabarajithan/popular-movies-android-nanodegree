package com.popularmovies.util;

import com.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class JsonUtil {

    private static final String KEY_MOVIE_RESULTS = "results";

    private static final String KEY_MOVIE_TITLE = "title";
    private static final String KEY_MOVIE_IMAGE = "poster_path";
    private static final String KEY_MOVIE_SYNOPSIS = "overview";
    private static final String KEY_MOVIE_RELEASE_DATE = "release_date";
    private static final String KEY_MOVIE_RATING = "vote_average";

    public static List<Movie> fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {

            List<Movie> movies = new ArrayList<>();
            JSONArray results = new JSONObject(json).optJSONArray(KEY_MOVIE_RESULTS);
            if (results == null) {
                return null;
            }

            for (int i = 0; i < results.length(); i++) {
                JSONObject movieObj = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setTitle(movieObj.optString(KEY_MOVIE_TITLE));
                movie.setImageUrl(movieObj.optString(KEY_MOVIE_IMAGE));
                movie.setSynopsis(movieObj.optString(KEY_MOVIE_SYNOPSIS));
                movie.setReleaseDate(movieObj.optString(KEY_MOVIE_RELEASE_DATE));
                movie.setRating(movieObj.optDouble(KEY_MOVIE_RATING));
                movies.add(movie);
            }

            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
