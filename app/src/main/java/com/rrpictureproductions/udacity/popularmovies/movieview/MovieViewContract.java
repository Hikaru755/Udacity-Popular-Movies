package com.rrpictureproductions.udacity.popularmovies.movieview;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.rrpictureproductions.udacity.popularmovies.model.Movie;

class MovieViewContract {

    interface View {
        void setMovie(Movie movie);
        void setPosterBitmap(Bitmap poster);
        void showConnectionError(boolean error);
        void showHighResLoadingFailedIndicator(boolean error);
        void showPosterLoadingIndicator(boolean show);
        ImageView getMoviePosterThumbnailTarget();
    }

    interface Presenter {
        void bindView(View view);
    }
}
