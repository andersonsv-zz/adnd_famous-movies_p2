package br.com.andersonv.famousmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {

    @SerializedName("results")
    private List<Review> reviews;

    @SerializedName("total_results")
    private int totalResults;

    public List<Review> getReviews() {
        return reviews;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
