package com.example.achmad.popmoviestwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.achmad.popmoviestwo.Api.MyApi;
import com.example.achmad.popmoviestwo.Data.Discover;
import com.example.achmad.popmoviestwo.Data.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Achmad on 06/05/2016.
 */
public class MovieFragment extends Fragment {
    ArrayList<Movie> mGridData = new ArrayList<Movie>();
    GridViewAdapter mGridAdapter;
    int mCurrentpage = 1;
    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        GridView mGridView = (GridView) v.findViewById(R.id.gridView);

        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.content_main, mGridData);

        fetchMovie(mCurrentpage);

        mGridView.setAdapter(mGridAdapter);
        return v;
    }

    public void fetchMovie(int currentPage) {
        if(currentPage < 2){
            mCurrentpage = 1;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi tmdbApi = retrofit.create(MyApi.class);

        // parameter apa aja yg dibutuhkan, bisa dilihat disini ya
        // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
        Call<Discover> call = tmdbApi.discoverMovies("popularity.desc",
                BuildConfig.TMDB_API_KEY,mCurrentpage);

        call.enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(Call<Discover> call, Response<Discover> response) {
                StringBuilder sb = new StringBuilder();
                for(Movie movie : response.body().getMovies()){
                    Log.d("gambar","http://image.tmdb.org/t/p/w185"+movie.getPosterPath());
                    mGridAdapter.add(movie);
                }
            }

            @Override
            public void onFailure(Call<Discover> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
