package com.rrpictureproductions.udacity.popularmovies.mainview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rrpictureproductions.udacity.popularmovies.R;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;
import com.rrpictureproductions.udacity.popularmovies.movieview.MovieActivity;
import com.rrpictureproductions.udacity.popularmovies.network.Secrets;
import com.rrpictureproductions.udacity.popularmovies.util.EndlessRecyclerViewScrollListener;
import com.rrpictureproductions.udacity.popularmovies.util.GridItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieGridFragment extends Fragment
        implements MovieGridAdapter.OnMovieClickedListener, MovieGridContract.View {

    public static final String TAG = MovieGridFragment.class.getSimpleName();

    private static final int gridCols = 2;

    private SwipeRefreshLayout refreshLayout;
    private MovieGridAdapter adapter;

    private MovieGridContract.Presenter presenter;
    private RecyclerView movieGrid;
    private View errorMessage;

    public MovieGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment MovieGridFragment.
     */
    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        initMovieGrid(v);
        initRefreshLayout(v);
        initPresenter();
        return v;
    }

    private void initPresenter() {
        presenter = new MovieGridPresenter();
        presenter.bindView(this);
    }

    private void initRefreshLayout(View v) {
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                presenter.refresh();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    private void initMovieGrid(View v) {
        adapter = new MovieGridAdapter(getActivity());
        adapter.registerOnMovieClickedListener(this);

        movieGrid = (RecyclerView) v.findViewById(R.id.movieGrid);
        movieGrid.setAdapter(adapter);
        movieGrid.addItemDecoration(new GridItemDecoration(gridCols, dip(4)));
        GridLayoutManager glm = new GridLayoutManager(getActivity(), gridCols);
        movieGrid.setLayoutManager(glm);
        movieGrid.addOnScrollListener(new EndlessRecyclerViewScrollListener(glm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                presenter.loadMore();
            }
        });

        errorMessage = v.findViewById(R.id.disconnectedMessage);
    }

    @Override
    public void clearMovies() {
        adapter.setMovies(null);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        adapter.addMovies(movies);
    }

    @Override
    public void showLoadingIndicator(boolean loading) {
        refreshLayout.setRefreshing(loading);
    }

    @Override
    public void showError(boolean show) {
        errorMessage.setVisibility(show ? View.VISIBLE : View.GONE);
        movieGrid.setVisibility(show ? View.GONE : View.VISIBLE);
        // TODO
    }

    private int dip(int px) {
        return Math.round(px * getResources().getDisplayMetrics().density);
    }

    void sortBySelected(String sortBy) {
        presenter.sortBySelected(sortBy);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movie.id);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_TITLE, movie.title);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_THUMBNAIL_URI,
                movie.getPosterThumbnailUri(Secrets.TMDB_API_KEY));
        startActivity(intent);
    }
}
