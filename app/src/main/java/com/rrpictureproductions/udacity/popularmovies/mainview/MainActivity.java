package com.rrpictureproductions.udacity.popularmovies.mainview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rrpictureproductions.udacity.popularmovies.Dependencies;
import com.rrpictureproductions.udacity.popularmovies.R;
import com.rrpictureproductions.udacity.popularmovies.network.TMDBClient;

public class MainActivity extends AppCompatActivity {

    private MovieGridFragment movieGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.INSTANCE.init(this);
        setContentView(R.layout.activity_main);
        setTitle(R.string.popular_movies);
        movieGrid = MovieGridFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, movieGrid)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                movieGrid.sortBySelected(TMDBClient.POPULARITY);
                setTitle(R.string.popular_movies);
                return true;
            case R.id.action_sort_top_rated:
                movieGrid.sortBySelected(TMDBClient.TOP_RATED);
                setTitle(R.string.top_rated_movies);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
