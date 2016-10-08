package com.rrpictureproductions.udacity.popularmovies.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;

import org.joda.time.LocalDate;

/**
 * Created by robin on 06.09.2016.
 */
public class Movie implements Parcelable {
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

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        adult = in.readByte() != 0;
        genreIds = in.createIntArray();
        original_title = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        runtime = in.readInt();
        tagline = in.readString();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeIntArray(genreIds);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(backdrop_path);
        parcel.writeDouble(popularity);
        parcel.writeInt(runtime);
        parcel.writeString(tagline);
        parcel.writeInt(vote_count);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeFloat(vote_average);
    }
}
