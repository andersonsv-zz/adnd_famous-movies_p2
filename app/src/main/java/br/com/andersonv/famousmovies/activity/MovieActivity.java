package br.com.andersonv.famousmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.andersonv.famousmovies.BuildConfig;
import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.adapter.MovieRecyclerViewAdapter;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.data.MovieSearch;
import br.com.andersonv.famousmovies.data.Movies;
import br.com.andersonv.famousmovies.network.MovieService;
import br.com.andersonv.famousmovies.network.RetrofitClientInstance;
import br.com.andersonv.famousmovies.util.NetworkUtils;
import br.com.andersonv.famousmovies.util.UiUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class MovieActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.MovieRecyclerOnClickHandler, LoaderManager.LoaderCallbacks<List<Movie>> {

    //component config
    private static final String SAVE_STATE_OBJECT_NAME = "movies";

    //components
    @BindView(R.id.tvErrorMessage)
    TextView mErrorMessageDisplay;
    @BindView(R.id.llInternetAccessError)
    LinearLayout mLlInternetAccessError;
    @BindView(R.id.pbIndicador)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    @BindView(R.id.parentMovieActivity)
    RelativeLayout movieActivity;

    private MovieRecyclerViewAdapter mMovieAdapter;

    private static final int MOVIE_LOADER_ID = 0;

    private List<Movie> movies = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);
        context = this;


        if (savedInstanceState == null || !savedInstanceState.containsKey(SAVE_STATE_OBJECT_NAME)) {
            movies = new ArrayList<>(movies);
            loadMovieData(MovieSearch.TOP_RATED);
        } else {
            movies = savedInstanceState.getParcelableArrayList(SAVE_STATE_OBJECT_NAME);

            mMovieAdapter = new MovieRecyclerViewAdapter(context, movies, MovieActivity.this);
            rvMovies.setAdapter(mMovieAdapter);
            showMovieDataView();

        }

        //code copied from https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
        int mNoOfColumns = UiUtils.calculateNoOfColumns(getApplicationContext());
        GridLayoutManager glm = new GridLayoutManager(this, mNoOfColumns);

        rvMovies.setLayoutManager(glm);
        rvMovies.setHasFixedSize(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_STATE_OBJECT_NAME, (ArrayList<? extends Parcelable>) movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.list_movies_toprated) {
            this.setTitle(R.string.top_rated);
            loadMovieData(MovieSearch.TOP_RATED);

            return true;
        }

        if (id == R.id.list_movies_mostpopular) {
            this.setTitle(R.string.most_popular);
            loadMovieData(MovieSearch.MOST_POPULAR);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        rvMovies.setVisibility(View.VISIBLE);
        mLlInternetAccessError.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        rvMovies.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void loadMovieData(@Nullable final MovieSearch movieSearch) {

        showMovieDataView();

        //clean list
        rvMovies.setAdapter(null);

        //check internet access
        if (!NetworkUtils.isNetworkConnected(this)) {
            mLlInternetAccessError.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            Snackbar snackbar = Snackbar.make(movieActivity, R.string.offline_no_internet, Snackbar.LENGTH_LONG)
                    .setAction(R.string.offline_no_internet_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadMovieData(movieSearch);
                        }
                    });
            snackbar.show();

        } else {
            mLoadingIndicator.setVisibility(View.VISIBLE);

            Bundle bundleForLoader = new Bundle();
            bundleForLoader.putInt(Intent.EXTRA_KEY_EVENT, movieSearch.ordinal());

            /*
             * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
             * created and (if the activity/fragment is currently started) starts the loader. Otherwise
             * the last created loader is re-used.
             */
            int loaderId = MOVIE_LOADER_ID;
            LoaderManager.LoaderCallbacks<List<Movie>> callback = MovieActivity.this;

            getSupportLoaderManager().restartLoader(loaderId, bundleForLoader, callback);

        }
    }

    @Override
    public void onClick(Movie movie) {

        Context context = this;
        Class destinationClass = MovieDetailActivity.class;

        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Intent.EXTRA_INTENT, movie);

        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {

            List<Movie> mMovie = null;

            @Override
            protected void onStartLoading() {
                if(mMovie != null){
                    deliverResult(mMovie);
                }else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Movie> loadInBackground() {
                String locale = Locale.getDefault().toString().replace("_", "-");

                MovieService service = RetrofitClientInstance.getRetrofitInstance().create(MovieService.class);
                Call<Movies> call;

                int movieSearch = MovieSearch.TOP_RATED.ordinal();

                if(args != null){
                    movieSearch = args.getInt(Intent.EXTRA_KEY_EVENT);
                }

                if (MovieSearch.TOP_RATED.ordinal() == movieSearch) {
                    call = service.getTopRated(1, locale, BuildConfig.API_MOVIE_DB_KEY);
                } else {
                    call = service.getPopular(1, locale, BuildConfig.API_MOVIE_DB_KEY);
                }

                try {

                    return call.execute().body().getMovies();

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter = new MovieRecyclerViewAdapter(context, data, MovieActivity.this);
        rvMovies.setAdapter(mMovieAdapter);

        if (null == data) {
            showErrorMessage();
        } else {
            movies = data;
            showMovieDataView();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }
}