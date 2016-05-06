package com.example.achmad.popmoviestwo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.achmad.popmoviestwo.Api.MyApi;
import com.example.achmad.popmoviestwo.BuildConfig;
import com.example.achmad.popmoviestwo.Data.Discover;
import com.example.achmad.popmoviestwo.Data.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> mGridData = new ArrayList<Movie>();
    GridViewAdapter mGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView mGridView = (GridView) findViewById(R.id.gridView);

        mGridAdapter = new GridViewAdapter(this, R.layout.content_main, mGridData);
        fetchMovie();
        mGridView.setAdapter(mGridAdapter);
    }

    public void fetchMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi tmdbApi = retrofit.create(MyApi.class);

        // parameter apa aja yg dibutuhkan, bisa dilihat disini ya
        // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
        Call<Discover> call = tmdbApi.discoverMovies("popularity.desc",
                BuildConfig.TMDB_API_KEY);

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
                Toast.makeText(MainActivity.this, "Failed to fetch movie", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
