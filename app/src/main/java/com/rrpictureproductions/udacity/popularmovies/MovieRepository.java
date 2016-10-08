package com.rrpictureproductions.udacity.popularmovies;

import android.util.SparseArray;

import com.rrpictureproductions.udacity.popularmovies.model.DiscoverResult;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;
import com.rrpictureproductions.udacity.popularmovies.network.Secrets;
import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;

import retrofit2.Call;
import retrofit2.Response;

public class MovieRepository {
    public static final String TAG = MovieRepository.class.getSimpleName();

    private SparseArray<Movie> movieCache = new SparseArray<>();
    private TMDBClient client;
    private String language;

    public MovieRepository(TMDBClient client, String language) {
        this.client = client;
        this.language = language;
    }

    public void discover(String sortBy, int page, final Callback<DiscoverResult> callback) {
        Call<DiscoverResult> call = client.discover(sortBy, page, language, Secrets.TMDB_API_KEY);
        call.enqueue(new retrofit2.Callback<DiscoverResult>() {
            @Override
            public void onResponse(Call<DiscoverResult> call, Response<DiscoverResult> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DiscoverResult> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getMovie(int id, Callback<Movie> callback) {
        getMovie(id, true, callback);
    }

    public void getMovie(int id, boolean withCache, final Callback<Movie> callback) {
        if(withCache) {
            Movie m = movieCache.get(id);
            if(m != null) {
                callback.onSuccess(m);
                return;
            }
        }
        client.getMovie(id, language, Secrets.TMDB_API_KEY).enqueue(new retrofit2.Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie result = response.body();
                movieCache.put(result.id, result);
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onFailure(Throwable t);
    }
}
