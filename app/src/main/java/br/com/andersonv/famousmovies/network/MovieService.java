package br.com.andersonv.famousmovies.network;

import br.com.andersonv.famousmovies.data.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MovieService {
    @GET("/3/movie/top_rated")
    Call<Movies> getTopRated(@Query("page") Integer page, @Query("language") String language, @Query("api_key") String apiKey);

    @GET("/3/movie/popular")
    Call<Movies> getPopular(@Query("page") Integer page, @Query("language") String language, @Query("api_key") String apiKey);
}
