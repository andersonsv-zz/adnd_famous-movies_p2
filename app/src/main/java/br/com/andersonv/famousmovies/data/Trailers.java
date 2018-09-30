package br.com.andersonv.famousmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @SerializedName("results")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
