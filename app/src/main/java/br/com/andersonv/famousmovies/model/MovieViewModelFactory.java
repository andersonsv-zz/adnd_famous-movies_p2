package br.com.andersonv.famousmovies.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import br.com.andersonv.famousmovies.database.AppDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final AppDatabase mDb;
    private final Long mMovieId;

    public MovieViewModelFactory(AppDatabase database, Long movieId) {
        mDb = database;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieViewModel(mDb, mMovieId);
    }
}
