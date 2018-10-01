package br.com.andersonv.famousmovies.activity;


import android.content.Intent;
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

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.util.GradientTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String IMAGE_BACKDROP_URL = "http://image.tmdb.org/t/p/w342/";

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

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
            Movie movie = intent.getParcelableExtra(Intent.EXTRA_INTENT);

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
}