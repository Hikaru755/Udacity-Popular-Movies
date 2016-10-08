package com.rrpictureproductions.udacity.popularmovies.network;

import com.rrpictureproductions.udacity.popularmovies.model.DiscoverResult;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by robin on 07.09.2016.
 */
public interface TMDBClient {
    String BASE_URI = "https://api.themoviedb.org/3/";
    String POSTER_BASE_URI = "http://image.tmdb.org/t/p/";

    String POPULARITY = "popular";
    String TOP_RATED = "top_rated";

    @GET("/3/movie/{category}")
    Call<DiscoverResult> discover(@Path("category")  String sortBy,
                                  @Query("page")     int    page,
                                  @Query("language") String language,
                                  @Query("api_key")  String apiKey);

    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id")        int    id,
                         @Query("language") String language,
                         @Query("api_key")  String apiKey);
}
