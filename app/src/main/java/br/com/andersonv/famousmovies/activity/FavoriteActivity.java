package br.com.andersonv.famousmovies.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.adapter.FavoriteRecyclerViewAdapter;
import br.com.andersonv.famousmovies.database.FavoriteEntry;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteRecyclerViewAdapter mFavoriteAdapter;

    private static final String TAG = FavoriteActivity.class.getSimpleName();

    @BindView(R.id.rvFavorites)
    RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mFavoriteAdapter = new FavoriteRecyclerViewAdapter(this);
        mRecycleView.setAdapter(mFavoriteAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecycleView.addItemDecoration(decoration);

        setupViewModel();
    }

    private void setupViewModel() {
        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoritesEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mFavoriteAdapter.setFavorites(favoritesEntries);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}