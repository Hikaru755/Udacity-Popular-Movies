package com.rrpictureproductions.udacity.popularmovies.model;

import android.net.Uri;

import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;

import org.joda.time.LocalDate;

/**
 * Created by robin on 06.09.2016.
 */
public class Movie {
    public int id;
    public String title;
    public String overview;
    public String poster_path;
    public boolean adult;
    public LocalDate release_date;
    public int[] genreIds;
    public String original_title;
    public String original_language;
    public String backdrop_path;
    public double popularity;
    public int runtime;
    public String tagline;
    public int vote_count;
    public boolean video;
    public float vote_average;

    public Movie(int id, String title, String poster_path) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
    }

    public Uri getPosterThumbnailUri(String apiKey) {
        // Todo factor the width out into resolution-dependent string resources
        return getPosterUri(apiKey, "w342");
    }

    public Uri getPosterHiReslUri(String apiKey) {
        return getPosterUri(apiKey, "original");
    }

    private Uri getPosterUri(String apiKey, String size) {
        if(poster_path == null) return null;
        return Uri.parse(TMDBClient.POSTER_BASE_URI).buildUpon()
                .appendPath(size)
                .appendPath(poster_path.substring(1))
                .appendQueryParameter("api_key", apiKey)
                .build();
    }
}
