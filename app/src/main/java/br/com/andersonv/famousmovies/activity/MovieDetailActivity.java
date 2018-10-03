package br.com.andersonv.famousmovies.activity;


import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.andersonv.famousmovies.BuildConfig;
import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.adapter.ReviewItemAdapter;
import br.com.andersonv.famousmovies.adapter.TrailerItemAdapter;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.data.Review;
import br.com.andersonv.famousmovies.data.Reviews;
import br.com.andersonv.famousmovies.data.Trailer;
import br.com.andersonv.famousmovies.data.Trailers;
import br.com.andersonv.famousmovies.network.MovieService;
import br.com.andersonv.famousmovies.network.RetrofitClientInstance;
import br.com.andersonv.famousmovies.util.GradientTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String IMAGE_BACKDROP_URL = "http://image.tmdb.org/t/p/w342/";

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private static final int TRAILER_LOADER_ID = 0;
    private static final int REVIEW_LOADER_ID = 1;

    @BindView(R.id.ivBackdrop)
    ImageView ivBackdrop;
    @BindView(R.id.ivPoster)
    ImageView ivPoster;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRelease)
    TextView tvRelease;
    @BindView(R.id.tvVoteAverage)
    TextView tvVoteAverage;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.lvTrailers)
    ListView lvTrailers;
    @BindView(R.id.lvReviews)
    ListView lvReviews;
    @BindView(R.id.pbTrailer)
    ProgressBar pbTrailer;
    @BindView(R.id.pbReview)
    ProgressBar pbReview;

    private Context context;

    private TrailerItemAdapter trailerAdapter;
    private ReviewItemAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        context = this;

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_INTENT)) {
            final Movie movie = intent.getParcelableExtra(Intent.EXTRA_INTENT);

            initLoaders(movie.getId());

            List<Transformation> transformations = new ArrayList<>();

            transformations.add(new GradientTransformation());

            Picasso.with(this)
                    .load(IMAGE_BACKDROP_URL + movie.getBackdropPath())
                    .transform(transformations)
                    .into(ivBackdrop);

            Picasso.with(this)
                    .load(IMAGE_URL + movie.getPosterPath())
                    .into(ivPoster);

            tvTitle.setText(movie.getTitle());
            this.setTitle(movie.getTitle());
            tvRelease.setText(movie.getReleaseDate());
            tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
            tvOverview.setText(movie.getOverview());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_header, menu);
        return true;
    }

    private void initLoaders(final Long id) {

        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putLong(Intent.EXTRA_INDEX, id);

        getLoaderManager().restartLoader(TRAILER_LOADER_ID, bundleForLoader, trailerLoaderCallbacks).forceLoad();
        getLoaderManager().restartLoader(REVIEW_LOADER_ID, bundleForLoader, reviewLoaderCallbacks).forceLoad();

    }

    private LoaderManager.LoaderCallbacks trailerLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Trailer>>() {

        @Override
        public android.content.Loader<List<Trailer>> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<List<Trailer>>(context) {
                @Nullable
                @Override
                public List<Trailer> loadInBackground() {

                    final Long movieId = args.getLong(Intent.EXTRA_INDEX);

                    MovieService service = RetrofitClientInstance.getRetrofitInstance().create(MovieService.class);
                    Call<Trailers> call;
                    call = service.getTrailers(movieId, BuildConfig.API_MOVIE_DB_KEY);

                    try {
                        return call.execute().body().getTrailers();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onStartLoading() {
                    forceLoad();
                    pbTrailer.setVisibility(View.VISIBLE);
                }
            };
        }

        @Override
        public void onLoadFinished(android.content.Loader<List<Trailer>> loader, List<Trailer> data) {
            if (data != null && !data.isEmpty()) {
                lvTrailers.setFocusable(false);

                trailerAdapter = new TrailerItemAdapter(context, data);
                lvTrailers.setAdapter(trailerAdapter);
                lvTrailers.setVisibility(View.VISIBLE);

                pbTrailer.setVisibility(View.INVISIBLE);

            } else {
               // reviewsLv.setVisibility(View.GONE);
               // reviewsLabelTv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<List<Trailer>> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks reviewLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Review>>() {

        @Override
        public android.content.Loader<List<Review>> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<List<Review>>(context) {
                @Nullable
                @Override
                public List<Review> loadInBackground() {

                    final Long movieId = args.getLong(Intent.EXTRA_INDEX);

                    MovieService service = RetrofitClientInstance.getRetrofitInstance().create(MovieService.class);
                    Call<Reviews> call;
                    call = service.getReviews(movieId, BuildConfig.API_MOVIE_DB_KEY);

                    try {
                        return call.execute().body().getReviews();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onStartLoading() {
                    forceLoad();
                    pbReview.setVisibility(View.VISIBLE);
                }
            };
        }

        @Override
        public void onLoadFinished(android.content.Loader<List<Review>> loader, List<Review> data) {
            if (data != null && !data.isEmpty()) {
                lvReviews.setFocusable(false);

                reviewAdapter = new ReviewItemAdapter(context, data);
                lvReviews.setAdapter(reviewAdapter);
                lvReviews.setVisibility(View.VISIBLE);

                pbReview.setVisibility(View.INVISIBLE);

            } else {
                // reviewsLv.setVisibility(View.GONE);
                // reviewsLabelTv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<List<Review>> loader) {

        }
    };
}