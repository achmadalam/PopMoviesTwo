package com.example.achmad.popmoviestwo.Api;

import com.example.achmad.popmoviestwo.Data.Discover;
import com.example.achmad.popmoviestwo.Movie_Detail.MovieDetail;
import com.example.achmad.popmoviestwo.Review.Review;
import com.example.achmad.popmoviestwo.Trailer.Videos;
import com.example.achmad.popmoviestwo.util.MovieConst;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Achmad on 06/05/2016.
 */
public interface MyApi {
    @GET("3/discover/movie")
    Call<Discover> discoverMovies(@Query("sort_by") String sortBy,
                                  @Query("api_key") String apiKey,@Query("page") int page);

    @GET("3/discover/movie")
    Call<Discover> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                  @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    @GET("3/discover/movie")
    Call<Discover> discoverMoviesPage(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                      @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                      @Query(MovieConst.PAGE_PARAM) int pageNumber
    );

    // sort by highest rated, require another two parameter
    // average votes and vote count (to make sure its not some
    // random movie with only a few people rate it 10) minimum of 1000 people
    @GET("3/discover/movie")
    Call<Discover> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                  @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                  @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                  @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount
    );

    @GET("3/discover/movie")
    Call<Discover> discoverMoviesPage(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                      @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                      @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                      @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount,
                                      @Query(MovieConst.PAGE_PARAM) int pageNumber
    );

    @GET("3/movie/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") int movieId,
                                     @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    // movie review id sample: 49026 - The Dark Knight
    @GET("3/movie/{id}/reviews")
    Call<Review> getMovieReviews(@Path("id") int movieId,
                                 @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    // movie trailer id sample: 550 - Fight Club
    @GET("3/movie/{id}/videos")
    Call<Videos> getMovieTrailers(@Path("id") int movieId,
                                  @Query(MovieConst.API_KEY_PARAM) String apiKey
    );
}
