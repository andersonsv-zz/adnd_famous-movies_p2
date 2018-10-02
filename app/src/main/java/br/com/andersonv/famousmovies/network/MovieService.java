package br.com.andersonv.famousmovies.network;

import br.com.andersonv.famousmovies.data.Movies;
import br.com.andersonv.famousmovies.data.Reviews;
import br.com.andersonv.famousmovies.data.Trailers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("/3/movie/top_rated")
    Call<Movies> getTopRated(@Query("page") Integer page, @Query("language") String language, @Query("api_key") String apiKey);

    @GET("/3/movie/popular")
    Call<Movies> getPopular(@Query("page") Integer page, @Query("language") String language, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<Reviews> getReviews(@Path("id") long id);

    @GET("3/movie/{id}/videos")
    Call<Trailers> getTrailers(@Path("id") long id);

}
