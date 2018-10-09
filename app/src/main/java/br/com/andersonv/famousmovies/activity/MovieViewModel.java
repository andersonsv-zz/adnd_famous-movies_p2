package br.com.andersonv.famousmovies.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import br.com.andersonv.famousmovies.database.AppDatabase;
import br.com.andersonv.famousmovies.database.FavoriteEntry;

class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private LiveData<FavoriteEntry> favorite;

    public MovieViewModel(AppDatabase database, Long movieId) {
        favorite = database.favoriteDao().loadFavoriteById(movieId);
    }

    public LiveData<FavoriteEntry> getFavorite() {
        return favorite;
    }
}
