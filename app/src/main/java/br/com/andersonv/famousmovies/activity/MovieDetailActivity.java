package br.com.andersonv.famousmovies.activity;


import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.andersonv.famousmovies.BuildConfig;
import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.adapter.MovieRecyclerViewAdapter;
import br.com.andersonv.famousmovies.adapter.ReviewRecyclerViewAdapter;
import br.com.andersonv.famousmovies.adapter.TrailerRecyclerViewAdapter;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.data.Review;
import br.com.andersonv.famousmovies.data.Reviews;
import br.com.andersonv.famousmovies.data.Trailer;
import br.com.andersonv.famousmovies.data.Trailers;
import br.com.andersonv.famousmovies.database.AppDatabase;
import br.com.andersonv.famousmovies.database.FavoriteEntry;
import br.com.andersonv.famousmovies.network.MovieService;
import br.com.andersonv.famousmovies.network.RetrofitClientInstance;
import br.com.andersonv.famousmovies.tasks.AppExecutors;
import br.com.andersonv.famousmovies.util.GradientTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class MovieDetailActivity extends AppCompatActivity implements TrailerRecyclerViewAdapter.TrailerRecyclerOnClickHandler {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String IMAGE_BACKDROP_URL = "http://image.tmdb.org/t/p/w342/";

    private static final String YOUTUBE_URL_SHARE = "https://youtu.be/";

    private static final String YOUTUBE_WEB_OPEN = "http://www.youtube.com/watch?v=";
    private static final String YOUTUBE_APP_OPEN = "vnd.youtube:";


    private static final String TYPE_SHARE = "text/plain";

    private static final int TRAILER_LOADER_ID = 0;
    private static final int REVIEW_LOADER_ID = 1;

    private AppDatabase mDb;

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
    @BindView(R.id.rvTrailers)
    RecyclerView rvTrailers;
    @BindView(R.id.rvReviews)
    RecyclerView rvReviews;
    @BindView(R.id.pbTrailer)
    ProgressBar pbTrailer;
    @BindView(R.id.pbReview)
    ProgressBar pbReview;

    @BindView(R.id.btFavorite)
    ImageButton btFavorite;

    private Context context;
    private String firstTrailerYouTube;

    private TrailerRecyclerViewAdapter trailerAdapter;
    private ReviewRecyclerViewAdapter reviewAdapter;

    private FavoriteEntry favorite;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        context = this;
        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_INTENT)) {
            populateUI(intent);
        }

    }

    private void populateUI(Intent intent){
        movie = intent.getParcelableExtra(Intent.EXTRA_INTENT);

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

        MovieViewModelFactory factory = new MovieViewModelFactory(mDb, movie.getId());

        final MovieViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        viewModel.getFavorite().observe(this, new Observer<FavoriteEntry>() {
            @Override
            public void onChanged(@Nullable FavoriteEntry favoriteEntry) {
                if(favoriteEntry != null) {
                    favorite = favoriteEntry;

                    btFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_pink_24dp));
                }else{
                    btFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_border_black_24dp));
                }

            }
        });
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

                firstTrailerYouTube = data.iterator().next().getKey();

                Log.d(TAG, "Trailers: " + data.size());

                rvTrailers.setFocusable(false);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rvTrailers.setLayoutManager(linearLayoutManager);

                trailerAdapter = new TrailerRecyclerViewAdapter(context, data, MovieDetailActivity.this);

                rvTrailers.setAdapter(trailerAdapter);
                rvTrailers.setVisibility(View.VISIBLE);

                pbTrailer.setVisibility(View.INVISIBLE);

            } else {
                //TODO - incluir mensagem de erro se não houver filmes
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

                Log.d(TAG, "Reviews: " + data.size());

                rvReviews.setFocusable(false);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rvReviews.setLayoutManager(linearLayoutManager);

                reviewAdapter = new ReviewRecyclerViewAdapter(context, data);
                rvReviews.setAdapter(reviewAdapter);
                rvReviews.setVisibility(View.VISIBLE);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_share){

            if(firstTrailerYouTube != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(TYPE_SHARE);

                intent.putExtra(Intent.EXTRA_TEXT, YOUTUBE_URL_SHARE + firstTrailerYouTube);
                startActivity(Intent.createChooser(intent, getString(R.string.detail_menu_share)));
            }
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return true;
    }

    public void onClickFavorite(View view){

        if(favorite == null){
            //insert
            Date dateRelease = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dateRelease = new Date(format.parse(movie.getReleaseDate()).getTime());
            }catch (Exception e){

            }

            favorite = new FavoriteEntry(movie.getId(),movie.getTitle(), movie.getPosterPath(), movie.getOverview(), movie.getVoteAverage(), dateRelease, new Date());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteDao().insertFavorite(favorite);
                }
            });
            btFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_pink_24dp));

        }else{
            //delete
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteDao().deleteFavorite(favorite);

                }
            });
            btFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_border_black_24dp));
        }
    }

    @Override
    public void onClick(Trailer trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_OPEN + trailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_WEB_OPEN + trailer.getKey()));

        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}