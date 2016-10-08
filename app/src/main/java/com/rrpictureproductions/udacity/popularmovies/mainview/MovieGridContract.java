package com.rrpictureproductions.udacity.popularmovies.mainview;

import com.rrpictureproductions.udacity.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by robin on 06.10.2016.
 */

final class MovieGridContract {
    interface View {
        void clearMovies();
        void addMovies(List<Movie> movies);
        void showLoadingIndicator(boolean loading);
        void showError(boolean show);
    }

    interface Presenter {
        void bindView(View view);
        void refresh();
        void sortBySelected(String sortBy);
        void loadMore();
    }
}
