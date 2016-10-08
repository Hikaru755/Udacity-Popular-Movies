package com.rrpictureproductions.udacity.popularmovies.movieview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.rrpictureproductions.udacity.popularmovies.Dependencies;
import com.rrpictureproductions.udacity.popularmovies.MovieRepository;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;
import com.rrpictureproductions.udacity.popularmovies.network.Secrets;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

class MoviePresenter implements MovieViewContract.Presenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();

    private int movieId;
    private MovieViewContract.View view;
    private Picasso picasso;

    MoviePresenter(int movieId) {
        this.movieId = movieId;
        picasso = Dependencies.INSTANCE.getPicasso();
    }

    @Override
    public void bindView(MovieViewContract.View view) {
        this.view = view;
        loadMovie(movieId);
    }


    private void loadMovie(int id) {
        if(!Dependencies.INSTANCE.getNetworkState().isOnline()) {
            view.showPosterLoadingIndicator(false);
            view.showHighResLoadingFailedIndicator(true);
            view.showConnectionError(true);
        }
        Dependencies.INSTANCE.getMovies().getMovie(id, new MovieRepository.Callback<Movie>() {
            @Override
            public void onSuccess(Movie result) {
                view.showConnectionError(false);
                view.setMovie(result);
                picasso.load(result.getPosterHiReslUri(Secrets.TMDB_API_KEY))
                        .into(moviePosterTarget);
            }

            @Override
            public void onFailure(Throwable t) {
                view.showConnectionError(true);
                Log.e(TAG, "Call failed :(", t);
            }
        });
    }

    /* Declaring this here as a strong reference so it doesn't get picked up by the GC, as picasso
    *  only stores a weak reference to targets internally */
    private Target moviePosterTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            view.setPosterBitmap(bitmap);
            view.showPosterLoadingIndicator(false);
            view.showHighResLoadingFailedIndicator(false);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            view.showPosterLoadingIndicator(false);
            view.showHighResLoadingFailedIndicator(true);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) { }
    };
}
