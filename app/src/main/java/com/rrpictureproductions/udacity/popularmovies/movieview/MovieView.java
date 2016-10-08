package com.rrpictureproductions.udacity.popularmovies.movieview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrpictureproductions.udacity.popularmovies.R;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;

class MovieView implements MovieViewContract.View {

    private Context ctx;

    private TextView released;
    private TextView length;
    private TextView originalTitle;
    private TextView tagline;
    private TextView rating;
    private ImageView ratingImage;
    private TextView summary;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView moviePoster;
    private View hiresPosterLoadingIndicator;
    private View loadingFailedIndicator;
    private View disconnectedMessage;
    private ViewGroup movieDetails;

    MovieView(Context context) {
        this.ctx = context.getApplicationContext();
    }

    void setView(View root, String initialTitle) {
        moviePoster = (ImageView) root.findViewById(R.id.moviePoster);
        hiresPosterLoadingIndicator = root.findViewById(R.id.hiresLoadingIndicator);
        loadingFailedIndicator = root.findViewById(R.id.loadingFailedIndicator);
        disconnectedMessage = root.findViewById(R.id.disconnectedMessage);
        movieDetails = (ViewGroup) root.findViewById(R.id.movieDetails);
        collapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(initialTitle);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(ctx, R.color.transparentWhite));
        released = (TextView) root.findViewById(R.id.released);
        length = (TextView) root.findViewById(R.id.length);
        originalTitle = (TextView) root.findViewById(R.id.originalTitle);
        tagline = (TextView) root.findViewById(R.id.tagline);
        rating = (TextView) root.findViewById(R.id.rating);
        ratingImage = (ImageView) root.findViewById(R.id.ratingImage);
        summary = (TextView) root.findViewById(R.id.summary);
    }

    @Override
    public void setMovie(Movie movie) {
        collapsingToolbarLayout.setTitle(movie.title);
        released.setText(String.valueOf(movie.release_date.getYear()));
        length.setText(ctx.getString(R.string.runtime_format, movie.runtime));
        originalTitle.setText(movie.original_title);
        if(movie.tagline == null || movie.tagline.isEmpty()) {
            tagline.setVisibility(View.GONE);
        }
        tagline.setText(movie.tagline);
        float votes = movie.vote_average;
        rating.setText(ctx.getString(R.string.rating_format, votes));
        ratingImage.setImageResource(getDrawableForRating(votes));
        summary.setText(movie.overview);
    }

    @Override
    public void setPosterBitmap(Bitmap poster) {
        moviePoster.setImageBitmap(poster);
    }

    @Override
    public void showConnectionError(boolean error) {
        movieDetails.setVisibility(error ? View.GONE : View.VISIBLE);
        disconnectedMessage.setVisibility(error ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showHighResLoadingFailedIndicator(boolean error) {
        loadingFailedIndicator.setVisibility(error ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showPosterLoadingIndicator(boolean show) {
        hiresPosterLoadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public ImageView getMoviePosterThumbnailTarget() {
        return moviePoster;
    }

    private @DrawableRes
    int getDrawableForRating(float rating) {
        if(rating >= 8) {
            return R.drawable.ic_emoticon_grey600_24dp;
        } else if(rating >= 6) {
            return R.drawable.ic_emoticon_happy_grey600_24dp;
        } else if(rating >= 4) {
            return R.drawable.ic_emoticon_neutral_grey600_24dp;
        } else if(rating >= 2) {
            return R.drawable.ic_emoticon_sad_grey600_24dp;
        } else {
            return R.drawable.ic_emoticon_dead_grey600_24dp;
        }
    }
}
