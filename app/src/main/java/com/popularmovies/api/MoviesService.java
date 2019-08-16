package com.popularmovies.api;

import com.popularmovies.BuildConfig;
import com.popularmovies.model.Movie;
import com.popularmovies.model.Review;
import com.popularmovies.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Abarajithan
 */
public interface MoviesService {

    @GET("{criteria}?api_key=" + BuildConfig.TMDB_API)
    Call<ApiResult<Movie>> listMoviesBy(@Path("criteria") String criteria);

    @GET("{movieId}/videos?api_key=" + BuildConfig.TMDB_API)
    Call<ApiResult<Trailer>> getTrailersFor(@Path("movieId") long movieId);

    @GET("{movieId}/reviews?api_key=" + BuildConfig.TMDB_API)
    Call<ApiResult<Review>> getReviewsFor(@Path("movieId") long movieId);

}
