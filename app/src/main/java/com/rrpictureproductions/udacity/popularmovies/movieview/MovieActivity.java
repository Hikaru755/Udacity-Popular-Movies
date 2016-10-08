package com.rrpictureproductions.udacity.popularmovies.movieview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.rrpictureproductions.udacity.popularmovies.Dependencies;
import com.rrpictureproductions.udacity.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "movie_id";
    public static final String EXTRA_MOVIE_TITLE = "movie_title";
    public static final String EXTRA_MOVIE_THUMBNAIL_URI = "movie_thumbnail";
    private MovieViewContract.Presenter presenter;
    private MovieView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.INSTANCE.init(this);
        setContentView(R.layout.activity_movie);
        setupSlidr();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            throw new IllegalArgumentException("MovieActivity can't be started without extras");
        }
        initAppbar();
        int movieId = extras.getInt(EXTRA_MOVIE_ID);
        String title = extras.getString(EXTRA_MOVIE_TITLE, getString(R.string.loading));
        Uri thumbnailUri = (Uri) extras.get(EXTRA_MOVIE_THUMBNAIL_URI);
        initView(movieId, title, thumbnailUri);
    }

    private void initView(int movieId, String title, Uri thumbnailUri) {
        view = new MovieView(this);
        view.setView(findViewById(R.id.coordinator), title);
        if(thumbnailUri != null) {
            Dependencies.INSTANCE.getPicasso().load(thumbnailUri)
                    .priority(Picasso.Priority.HIGH)
                    .into(view.getMoviePosterThumbnailTarget());
        }
        presenter = new MoviePresenter(movieId);
        presenter.bindView(view);
    }

    private void initAppbar() {
        setSupportActionBar((Toolbar)findViewById(R.id.appbar));
        ActionBar appBar = getSupportActionBar();
        if(appBar != null) {
            appBar.setDisplayShowHomeEnabled(true);
            appBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupSlidr() {
        SlidrConfig config = new SlidrConfig.Builder()
                .sensitivity(0.3f)
                .build();
        Slidr.attach(this, config);
    }
}
