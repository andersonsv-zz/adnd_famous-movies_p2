package br.com.andersonv.famousmovies.activity;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.andersonv.famousmovies.BuildConfig;
import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.data.MovieSearch;
import br.com.andersonv.famousmovies.data.Movies;
import br.com.andersonv.famousmovies.data.Reviews;
import br.com.andersonv.famousmovies.data.Trailers;
import br.com.andersonv.famousmovies.network.MovieService;
import br.com.andersonv.famousmovies.network.RetrofitClientInstance;
import br.com.andersonv.famousmovies.util.GradientTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

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

        getLoaderManager().initLoader(TRAILER_LOADER_ID, bundleForLoader, this);
        getLoaderManager().initLoader(REVIEW_LOADER_ID, bundleForLoader, this);
    }


    @Override
    public Loader onCreateLoader(int id, final Bundle args) {

        Long movieId = args.getLong(Intent.EXTRA_INDEX);

        if (id == TRAILER_LOADER_ID){
            MovieService service = RetrofitClientInstance.getRetrofitInstance().create(MovieService.class);
            Call<Trailers> call;

            call = service.getTrailers(movieId);

            try {
                return (Loader) call.execute().body().getTrailers();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else if (id == REVIEW_LOADER_ID){
            MovieService service = RetrofitClientInstance.getRetrofitInstance().create(MovieService.class);
            Call<Reviews> call;

            call = service.getReviews(movieId);

            try {
                return (Loader) call.execute().body().getReviews();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == TRAILER_LOADER_ID){

        }
        else if(loader.getId() == REVIEW_LOADER_ID){

        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}