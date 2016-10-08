package com.rrpictureproductions.udacity.popularmovies.mainview;

import android.util.Log;

import com.rrpictureproductions.udacity.popularmovies.Dependencies;
import com.rrpictureproductions.udacity.popularmovies.MovieRepository;
import com.rrpictureproductions.udacity.popularmovies.model.DiscoverResult;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;
import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;

import java.util.List;

/**
 * Created by robin on 06.10.2016.
 */

final class MovieGridPresenter implements MovieGridContract.Presenter {

    private static final String TAG = MovieGridFragment.class.getSimpleName();

    private MovieGridContract.View view;

    private String sortBy = TMDBClient.POPULARITY;
    private int lastPage;

    @Override
    public void bindView(MovieGridContract.View view) {
        this.view = view;
        loadMovies();
    }

    @Override
    public void loadMore() {
        loadNextPage();
    }

    @Override
    public void refresh() {
        view.showLoadingIndicator(true);
        loadMovies();
    }

    private void loadMovies() {
        view.clearMovies();
        loadAndAddPage(1);
    }

    private void loadNextPage() {
        loadAndAddPage(lastPage + 1);
    }

    private void loadAndAddPage(int page) {
        if(!Dependencies.INSTANCE.getNetworkState().isOnline()) {
            view.showError(true);
            view.showLoadingIndicator(false);
            return;
        }
        Dependencies.INSTANCE.getMovies().discover(sortBy, page,
                new MovieRepository.Callback<DiscoverResult>() {
                    @Override
                    public void onSuccess(DiscoverResult result) {
                        view.showError(false);
                        lastPage = result.page;
                        view.showLoadingIndicator(false);
                        List<Movie> movies = result.results;
                        view.addMovies(movies);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Call failed :(", t);
                        view.showError(true);
                        view.showLoadingIndicator(false);
                    }
                });
    }

    public void sortBySelected(String sortBy) {
        if(!this.sortBy.equals(sortBy)) {
            // Only reload if we're actually changing the sort order
            this.sortBy = sortBy;
            loadMovies();
        }
    }
}
