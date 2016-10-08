package com.rrpictureproductions.udacity.popularmovies.mainview;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rrpictureproductions.udacity.popularmovies.Dependencies;
import com.rrpictureproductions.udacity.popularmovies.R;
import com.rrpictureproductions.udacity.popularmovies.model.Movie;
import com.rrpictureproductions.udacity.popularmovies.network.Secrets;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieTileViewHolder> {
    static final String TAG = MovieGridAdapter.class.getSimpleName();

    private List<Movie> movies;
    private Context ctx;
    private LayoutInflater inflater;
    private List<OnMovieClickedListener> onMovieClickedListeners = new ArrayList<>();
    private Picasso picasso;

    public MovieGridAdapter(Context context) {
        this.ctx = context.getApplicationContext();
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        picasso = Dependencies.INSTANCE.getPicasso();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {
        if(this.movies == null) {
            setMovies(movies);
            return;
        }
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public Movie getMovie(int position) {
        return movies.get(position);
    }

    @Override
    public MovieTileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.movie_poster_tile, parent, false);
        return new MovieTileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieTileViewHolder holder, int position) {
        final Movie m = getMovie(position);
        Uri posterUri = m.getPosterThumbnailUri(Secrets.TMDB_API_KEY);
        picasso.load(posterUri)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.ic_emoticon_sad_grey600_24dp)
                .into(holder.moviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OnMovieClickedListener listener : onMovieClickedListeners) {
                    listener.onMovieClicked(m);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movies == null) {
            return 0;
        }
        return movies.size();
    }

    class MovieTileViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        public MovieTileViewHolder(View root) {
            super(root);
            moviePoster = (ImageView) root.findViewById(R.id.posterImageView);
        }
    }

    public void registerOnMovieClickedListener(OnMovieClickedListener listener) {
        onMovieClickedListeners.add(listener);
    }

    public interface OnMovieClickedListener {
        void onMovieClicked(Movie movie);
    }
}
