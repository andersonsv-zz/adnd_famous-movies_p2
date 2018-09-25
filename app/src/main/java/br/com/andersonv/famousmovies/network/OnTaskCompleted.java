package br.com.andersonv.famousmovies.network;

import java.util.List;

import br.com.andersonv.famousmovies.data.Movie;

public interface OnTaskCompleted {
    void onTaskCompleted(List<Movie> response);
}
