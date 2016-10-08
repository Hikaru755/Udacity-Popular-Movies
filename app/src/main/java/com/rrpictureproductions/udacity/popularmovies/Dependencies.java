package com.rrpictureproductions.udacity.popularmovies;

import android.content.Context;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rrpictureproductions.udacity.popularmovies.network.NetworkState;
import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum Dependencies {
    INSTANCE;

    private Picasso picasso;
    private MovieRepository movieRepository;
    private NetworkState networkState;

    public void init(Context context) {
        if(!isInitialized()) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
            TMDBClient client = new Retrofit.Builder()
                    .baseUrl(TMDBClient.BASE_URI)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(TMDBClient.class);
            movieRepository = new MovieRepository(client, Locale.getDefault().getLanguage());
            picasso = Picasso.with(context.getApplicationContext());
            networkState = new NetworkState(context);
        }
    }

    private boolean isInitialized() {
        return picasso != null && movieRepository != null;
    }

    public Picasso getPicasso() { return picasso; }

    public MovieRepository getMovies() { return movieRepository; }

    public NetworkState getNetworkState() { return networkState; }
}
