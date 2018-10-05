package br.com.andersonv.famousmovies.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import br.com.andersonv.famousmovies.database.AppDatabase;
import br.com.andersonv.famousmovies.database.FavoriteEntry;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private LiveData<FavoriteEntry> favorite;

    public MovieViewModel(AppDatabase database, Long movieId) {
        favorite = database.favoriteDao().loadFavoriteById(movieId);
    }

    public LiveData<FavoriteEntry> getFavorite() {
        return favorite;
    }
}
