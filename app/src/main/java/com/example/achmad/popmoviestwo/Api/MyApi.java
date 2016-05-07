package com.example.achmad.popmoviestwo.Api;

import com.example.achmad.popmoviestwo.Data.Discover;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Achmad on 06/05/2016.
 */
public interface MyApi {
    @GET("3/discover/movie")
    Call<Discover> discoverMovies(@Query("sort_by") String sortBy,
                                  @Query("api_key") String apiKey,@Query("page") int page);

}
